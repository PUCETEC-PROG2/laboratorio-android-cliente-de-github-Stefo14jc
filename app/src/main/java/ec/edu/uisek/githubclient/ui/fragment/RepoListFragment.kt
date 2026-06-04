package ec.edu.uisek.githubclient.ui.fragment
// .

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ec.edu.uisek.githubclient.R
import ec.edu.uisek.githubclient.model.Repository
import ec.edu.uisek.githubclient.network.GitHubService
import ec.edu.uisek.githubclient.ui.adapter.RepoAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepoAdapter
    private lateinit var progressBar: ProgressBar
    
    // Token proporcionado por el usuario (Reemplazar con el token real)
    private val GITHUB_TOKEN = "Bearer YOUR_TOKEN_HERE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repo_list, container, false)
        
        recyclerView = view.findViewById(R.id.rvRepositories)
        progressBar = view.findViewById(R.id.progressBar)
        
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RepoAdapter(emptyList())
        recyclerView.adapter = adapter
        
        fetchRepositories()
        
        return view
    }

    private fun fetchRepositories() {
        progressBar.visibility = View.VISIBLE
        
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service = retrofit.create(GitHubService::class.java)
        
        service.getUserRepos(GITHUB_TOKEN).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.updateRepos(it)
                    }
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("RepoListFragment", "Error fetching repos", t)
                Toast.makeText(context, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
