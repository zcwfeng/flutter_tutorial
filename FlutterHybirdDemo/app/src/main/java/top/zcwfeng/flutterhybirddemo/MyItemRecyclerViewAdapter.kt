package top.zcwfeng.flutterhybirddemo

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.IntentCompat

import top.zcwfeng.flutterhybirddemo.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_use, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        holder.contentView.setOnClickListener {
            when(position){
                0-> {
                    var intent = Intent();
                    intent.setClass(holder.contentView.context,SingleFlutterViewActivity::class.java)
                    holder.contentView.context.startActivity(intent)
                }
                1-> {
                    var intent = Intent()
                    intent.setClass(holder.contentView.context,MultiFlutterViewActivity::class.java)
                    holder.contentView.context.startActivity(intent)
                }

            }
        }
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)



        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}