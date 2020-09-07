package id.rafif.aplikasitodogambar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import id.rafif.aplikasitodogambar.model.ModelData
import id.rafif.aplikasitodogambar.recyclerview.adapter.ItemDataAdapter
import id.rafif.aplikasitodogambar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // buat variabel adapter untuk recyclerview
    private lateinit var adapterMain: ItemDataAdapter

    private lateinit var valueEventListener: ValueEventListener

    private lateinit var databaseUser: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var inflater: LayoutInflater = layoutInflater
        binding = ActivityMainBinding.inflate(inflater)

        setContentView(binding.root)

        //isi databseUser dengan nama Lokasi yg dituju dalam Firebase
        databaseUser = FirebaseDatabase.getInstance().reference.child("Users")

        binding.extendedFab.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        adapterMain = ItemDataAdapter()

        // setting RecyclerView y
        binding.rvMain.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterMain
            setHasFixedSize(true)
        }

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // snapshot
                if(snapshot.childrenCount > 0) {
                    val daftarUser = arrayListOf<ModelData>()
                    for(dataUser in snapshot.children) {
                        val data = dataUser.getValue(ModelData::class.java) as ModelData
                        daftarUser.add(data)
                    }
                    // masukan data yg telah didapatkan ke dalam adapter recyclerview
                    adapterMain.addData(daftarUser)
                    // beri tahu adapter recyclerview jika ada perubahan data
                    adapterMain.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        // tambahkan valueEventListener ke databseUser
        databaseUser.addValueEventListener(valueEventListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        // ini jangan dihapus.. setiap kali kita menambahkan eventlistener
        // maka perlu dihapus dengan cara removeEventListener
        // jika penambahan terjadi di oncreate
        // maka hapusnya itu ada di onDestroy seperti kode di bawah ini
        databaseUser.removeEventListener(valueEventListener)
    }
}