package ec.edu.uisek.githubclient.ui.adapter
// .

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ec.edu.uisek.githubclient.R
import ec.edu.uisek.githubclient.model.Repository



class RepoAdapter(private var repos: List<Repository>) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = repos.size

    fun updateRepos(newRepos: List<Repository>) {
        this.repos = newRepos
        notifyDataSetChanged()
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvLanguage: TextView = itemView.findViewById(R.id.tvLanguage)
        private val ivOwner: ImageView = itemView.findViewById(R.id.ivOwner)

        fun bind(repo: Repository) {
            tvName.text = repo.name
            tvDescription.text = repo.description ?: "No description"
            tvLanguage.text = repo.language ?: "Unknown"
            
            repo.owner?.avatar_url?.let { url ->
                Glide.with(itemView.context)
                    .load(url)
                    .circleCrop()
                    .into(ivOwner)
            }
        }
    }
}
