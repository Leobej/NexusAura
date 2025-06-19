# Nexus Aura - Unified Social Media Platform

## Project Overview

**Nexus Aura** is an ambitious social media platform that amalgamates the standout features of leading applications like TikTok, Instagram, Facebook, and WhatsApp. Designed to foster seamless connections and enriched user interactions, Nexus Aura offers intuitive content sharing, personalized experiences, and robust community engagement, aiming to redefine digital communication and connectivity.

## Core Features

### 🔐 User Authentication & Profiles

- Secure registration and login processes with JWT-based authentication.
- Customizable user profiles showcasing personal information and interests.
- Privacy settings allowing users to control profile visibility.
- Follow, block, and report functionalities to manage user interactions.

### 📸 Content Sharing & Feed

- Creation of posts supporting text, images, and short videos.
- Interactive features: likes, comments, shares, and saves.
- Personalized feed displaying content from followed users and recommendations.
- Advanced filtering and sorting options for feed customization.

### 🎥 Live Streaming (Inspired by TikTok)

- Public live streaming capabilities for real-time audience engagement.
- Live chat during streams to foster community interaction.
- Discovery of live sessions through a dedicated live feed section.

### 💬 Messaging & Communication (Inspired by WhatsApp)

- Real-time one-on-one and group messaging with end-to-end encryption.
- Support for multimedia messages, including images, videos, and voice notes.
- Voice and video call functionalities.
- Status updates allowing users to share ephemeral content.

### 🛒 Marketplace (Inspired by Facebook)

- Platform for users to buy and sell products within the community.
- Product listings with images, descriptions, and pricing.
- Direct messaging between buyers and sellers.
- User ratings and reviews to build trust within transactions.

### 📢 Stories & Reels (Inspired by Instagram)

- Temporary stories that disappear after 24 hours, with options to highlight.
- Short-form video reels with editing tools and music integration.
- Explore section to discover trending stories and reels.

### 🔔 Notifications & Activity

- Real-time notifications for messages, likes, comments, follows, and more.
- Activity log to track user interactions and engagements.

## Technologies

### Backend

- **Kotlin with Spring Boot**: Scalable and efficient server-side development.
- **Spring Data JPA**: Streamlined database interactions.
- **PostgreSQL**: Robust relational database management.
- **WebSockets**: Real-time communication for messaging and live features.
- **RESTful API**: Communication between the backend and frontend.

### Android App

- **Kotlin**: Native Android development.
- **Retrofit**: Efficient network requests and API integration.
- **Glide**: Seamless image loading and caching.
- **Room**: Local data caching for offline accessibility.
- **Firebase Cloud Messaging (FCM)**: Push notifications.

### Web App

- **React.js**: Responsive and interactive user interface.
- **Axios**: Handling API calls and data fetching.
- **Redux**: State management.

### Containerization and Orchestration

- **Docker**: Containerizing applications for consistent environments.
- **Kubernetes**: Orchestrating containerized applications for scalability.

### CI/CD Pipeline

- **Jenkins**: Automating the software delivery process, including build, test, and deployment stages.

### Version Control

- **Git**: Distributed version control and collaboration.

## Development Focus

### Backend

- Implementing secure user authentication with JWT.
- Developing endpoints for comprehensive post management (CRUD).
- Establishing follow/unfollow functionalities.
- Building algorithms for personalized feed generation.
- Integrating real-time messaging and live streaming capabilities.
- Developing marketplace functionalities with product listings and transactions.

### Android App

- Facilitating user authentication and profile management.
- Enabling post creation with camera integration.
- Designing an engaging feed display with stories and reels.
- Implementing real-time messaging and notifications.
- Integrating live streaming features.

### Web App

- Implementing responsive user authentication interfaces.
- Allowing post creation with rich text editors and media uploads.
- Displaying a real-time feed with sorting and filtering.
- Providing user profile management.
- Enhancing user experience with advanced post filtering and discovery features.

### CI/CD

- Automating build, test, and deployment processes for continuous integration and delivery.

## Deployment Environment

Nexus Aura is deployed within a Kubernetes cluster, leveraging Docker containers to ensure consistent environments and scalability across different stages of development and production.

## CI/CD Pipeline (Jenkins)

- Monitoring the Git repository for code commits.
- Building backend and frontend applications.
- Creating Docker images and pushing them to a Docker registry.
- Deploying updated images to the Kubernetes cluster.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Node.js and npm (for web app)
- Android Studio (for Android app)
- Docker
- Kubernetes (optional, for deployment)
- Git

### Installation

1. Clone the repository: `git clone https://github.com/Leobej/NexusAura.git`
2. Navigate to the backend directory: `cd backend`
3. Build and run the backend application: `./mvnw spring-boot:run`
4. Navigate to the web app directory: `cd web-app`
5. Install dependencies: `npm install`
6. Start the web app: `npm start`
7. Open Android Studio and import the Android app project.
8. Build and run the Android application on an emulator or device.

### Deployment

1. Build Docker images for backend and frontend applications.
2. Push Docker images to a Docker registry.
3. Deploy Kubernetes manifests to the cluster.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the [MIT License](LICENSE).
