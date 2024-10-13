
# 🎃 Spooky Playlist Generator 🎃

## Project Overview

Spooky Playlist Generator is a Spring Boot application that creates personalized Halloween themed playlist based on the user's Spotify listening data. By analyzing the user's top tracks, artists and genres the app compares them with the top Halloween playlists from Spotify and curates a custom Halloween playlist that matches the user's musical preferences.

## Prerequisites

Before you start, make sure you have the following installed:

- **Java 11** or higher
- **Maven** (for dependency management)
- **Spotify Developer Account** (to get `Client ID`)

## Getting Started

1. **Clone the repository**

2. **Set up Spotify Developer App**:
   - Go to the [Spotify Developer Dashboard](https://developer.spotify.com/dashboard/applications).
   - Create a new application.
   - Note down your `Client ID`.
   - Set the redirect URI to `http://localhost:8080/callback`.

3. **Configure Environment Variables**:
   Go to the `application.properties` file in the root directory and add the following:
   ```
   com.spooky.playlist.client-id=your_client_id
   com.spooky.playlist.redirect-url=http://localhost:8080/callback
   ```

4. **Build the Project**:
   ```bash
   ./mvnw clean install
   ```

5. **Run the Application**:
   Run the file `PlaylistApplication.java`

6. **Access the Application**:
   Once the application is running, go to `http://localhost:8080` in your browser to start generating your spooky playlist.


## Future Enhancements

- **Custom Playlist Names**: Allow users to set their own names for the Halloween playlist.
- **Dynamic Image Selection**: Let users choose from different spooky images.
- **Track Filtering**: Provide options to include/exclude certain tracks based on mood or genre.
