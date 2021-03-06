package tech.fnplus.enrollme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tech.fnplus.enrollme.data.Event
import tech.fnplus.enrollme.data.EventResponse
import tech.fnplus.enrollme.data.EventValue
import tech.fnplus.enrollme.data.source.remote.APIServices
import tech.fnplus.enrollme.data.source.remote.RetrofitClient


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val retrofit = RetrofitClient.getClient("https://api.meetup.com/")
        val apiServices = retrofit.create(APIServices::class.java)
        val call = apiServices.loadEvents(BuildConfig.ApiKey).also {

            it.enqueue(object : Callback<EventResponse> {
                override fun onResponse(
                        call: Call<EventResponse>,
                        response: Response<EventResponse>
                ) {
                    val result = response.body()
                    if (result != null) {
                        Log.i("Result", result.toString())
                    } else {
                        Log.i("Result", "Null")
                    }
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.i("Result", "Fail")
                }
            })
        }

        return view
    }

}// Required empty public constructor

private fun <T> Call<T>.enqueue(callback: Callback<HashMap<String, EventValue>>) {

}
