# Quick Start Guide for LiraApp

## Prerequisites
- Android Studio Hedgehog or newer
- JDK 17 or higher
- A Supabase account (free tier available)

## Step-by-Step Setup

### 1. Clone the Repository
```bash
git clone https://github.com/PioBasile/LiraApp.git
cd LiraApp
```

### 2. Create Supabase Project

1. Go to [supabase.com](https://supabase.com) and sign up/login
2. Click "New Project"
3. Fill in project details (name, password, region)
4. Wait for project to be created

### 3. Set Up Database

In your Supabase project dashboard:

1. Go to "SQL Editor"
2. Click "New Query"
3. Paste this SQL:

```sql
-- Create expenses table
CREATE TABLE expenses (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES auth.users(id),
  amount DECIMAL NOT NULL,
  category TEXT NOT NULL,
  description TEXT NOT NULL,
  date BIGINT NOT NULL,
  created_at BIGINT NOT NULL
);

-- Enable Row Level Security
ALTER TABLE expenses ENABLE ROW LEVEL SECURITY;

-- Create policies
CREATE POLICY "Users can insert their own expenses"
  ON expenses FOR INSERT
  WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can view their own expenses"
  ON expenses FOR SELECT
  USING (auth.uid() = user_id);

CREATE POLICY "Users can update their own expenses"
  ON expenses FOR UPDATE
  USING (auth.uid() = user_id);

CREATE POLICY "Users can delete their own expenses"
  ON expenses FOR DELETE
  USING (auth.uid() = user_id);
```

4. Click "Run" to execute the SQL

### 4. Get Supabase Credentials

1. In Supabase dashboard, go to "Settings" → "API"
2. Copy your:
   - **Project URL** (e.g., `https://xxxxx.supabase.co`)
   - **anon/public key** (starts with `eyJ...`)

### 5. Configure the App

1. Open the project in Android Studio
2. Navigate to `app/src/main/java/com/liraapp/expense/di/AppModule.kt`
3. Replace the placeholders:

```kotlin
fun provideSupabaseClient(): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = "https://your-project.supabase.co",  // ← Replace this
        supabaseKey = "your-anon-key"                       // ← Replace this
    ) {
        install(Auth)
        install(Postgrest)
    }
}
```

### 6. Build and Run

1. Let Gradle sync (it will download dependencies - requires internet)
2. Connect an Android device or start an emulator
3. Click "Run" (Shift+F10) or use the green play button

### 7. Test the App

1. **Sign Up**: Create a new account with email and password
2. **Sign In**: Login with your credentials
3. **Add Expense**: Click the + button to add an expense
4. **View Summary**: See your monthly expenses and category breakdown
5. **Navigate Months**: Use < and > buttons to browse different months

## Troubleshooting

### Build Issues

**Problem**: "Could not resolve dependencies"
- **Solution**: Check internet connection and try again

**Problem**: "SDK not found"
- **Solution**: In Android Studio, go to File → Project Structure → SDK Location and set your Android SDK path

### Supabase Connection Issues

**Problem**: "Authentication failed"
- **Solution**: Double-check your Supabase URL and API key in AppModule.kt

**Problem**: "Cannot insert expense"
- **Solution**: Verify that RLS policies are correctly set up in Supabase

### App Crashes

**Problem**: App crashes on startup
- **Solution**: Check Logcat in Android Studio for error messages. Most likely a configuration issue.

## Next Steps

- Read [ARCHITECTURE.md](ARCHITECTURE.md) to understand the code structure
- Read [IMPLEMENTATION.md](IMPLEMENTATION.md) for detailed feature descriptions
- Check [README.md](README.md) for complete documentation

## Support

For issues or questions:
1. Check the documentation files (README, BUILD, IMPLEMENTATION, ARCHITECTURE)
2. Review the code comments
3. Check Supabase documentation at [supabase.com/docs](https://supabase.com/docs)

## Features to Try

✅ Sign up with a new account
✅ Add multiple expenses with different categories
✅ Browse expenses by month
✅ See category breakdown
✅ Sign out and sign in again (session persistence)

Enjoy tracking your expenses with LiraApp! 🎉
