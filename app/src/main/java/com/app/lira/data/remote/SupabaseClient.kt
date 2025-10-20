package com.app.lira.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
//import io.github.jan.supabase.gotrue.GoTrue
//import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    private const val SUPABASE_URL = "https://pbqkhpwcykllwnbfeobz.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBicWtocHdjeWtsbHduYmZlb2J6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTM2MzEyNTgsImV4cCI6MjA2OTIwNzI1OH0._IplXMcCEVXXEc0UrjKSRTuySsKOeS0ubb2Yj2s7n6k"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        //install(GoTrue)
        //install(Storage)
    }
}
