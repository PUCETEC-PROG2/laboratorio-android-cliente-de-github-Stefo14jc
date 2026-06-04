package ec.edu.uisek.githubclient.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ec.edu.uisek.githubclient.R
import ec.edu.uisek.githubclient.model.Repository
import com.google.android.material.textfield.TextInputEditText

class RepoFormFragment : Fragment() {





    private var editingRepo: Repository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editingRepo = it.getSerializable("repo") as Repository?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repo_form, container, false)
        
        val etName = view.findViewById<TextInputEditText>(R.id.etName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etDescription)
        val btnSave = view.findViewById<android.widget.Button>(R.id.btnSave)

        if (editingRepo != null) {
            etName.setText(editingRepo?.name)
            etDesc.setText(editingRepo?.description)
            etName.isEnabled = false
        }

        btnSave.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<android.widget.Button>(R.id.btnCancel).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
