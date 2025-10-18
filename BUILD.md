# Build Instructions

## Important Notes

This project requires internet connectivity to download dependencies from Maven Central and Google's Maven repository.

## Build Requirements

- **Java Development Kit (JDK)**: Version 17 or higher
- **Android SDK**: API Level 24 (Android 7.0) or higher
- **Gradle**: Version 8.0 (included via wrapper)
- **Internet Connection**: Required for downloading dependencies

## Building the Project

### Using Android Studio (Recommended)

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the project directory and select it
4. Wait for Gradle sync to complete (this will download all dependencies)
5. Configure Supabase credentials in `app/src/main/java/com/liraapp/expense/di/AppModule.kt`
6. Click "Run" or press Shift+F10

### Using Command Line

```bash
# On Linux/Mac
./gradlew assembleDebug

# On Windows
gradlew.bat assembleDebug
```

## Configuration

Before building, update the Supabase configuration in `app/src/main/java/com/liraapp/expense/di/AppModule.kt`:

```kotlin
@Provides
@Singleton
fun provideSupabaseClient(): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = "https://your-project.supabase.co",  // Replace with your Supabase project URL
        supabaseKey = "your-anon-key"                      // Replace with your Supabase anon key
    ) {
        install(Auth)
        install(Postgrest)
    }
}
```

## Troubleshooting

### Network Issues

If you encounter network errors when downloading dependencies:
- Check your internet connection
- Ensure your firewall allows connections to maven.google.com and repo1.maven.org
- Try using a VPN if certain repositories are blocked in your region

### Build Errors

If you encounter build errors:
1. Clean the project: `./gradlew clean`
2. Rebuild: `./gradlew assembleDebug`
3. Invalidate caches in Android Studio: File → Invalidate Caches / Restart

### Supabase Connection Issues

If the app can't connect to Supabase:
1. Verify your Supabase credentials are correct
2. Ensure your Supabase project is active
3. Check that the database table schema matches the one in README.md
4. Verify Row Level Security (RLS) policies are correctly set up

## Project Structure

```
LiraApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/liraapp/expense/
│   │       │   ├── domain/          # Business logic layer
│   │       │   │   ├── model/       # Domain models
│   │       │   │   ├── repository/  # Repository interfaces
│   │       │   │   └── usecase/     # Use cases
│   │       │   ├── data/            # Data layer
│   │       │   │   ├── remote/      # Supabase integration
│   │       │   │   └── repository/  # Repository implementations
│   │       │   ├── presentation/    # UI layer
│   │       │   │   ├── screens/     # Compose screens
│   │       │   │   └── navigation/  # Navigation setup
│   │       │   └── di/              # Dependency injection
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── wrapper/
```

## Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumentation tests (requires connected device or emulator):
```bash
./gradlew connectedAndroidTest
```
