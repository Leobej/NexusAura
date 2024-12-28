# Project: Nexus Aura

**Concept:**

Nexus Aura is a simplified social media application focused on text and image posts, basic user profiles, and following/follower functionality. This project serves as a comprehensive learning exercise, incorporating modern development practices and technologies.

**Features:**

*   Post creation (text and images)
*   User profiles
*   Following/followers
*   Basic feed display

**Technologies:**

*   **Backend:**
    *   Java with Spring Boot
    *   Spring Data JPA (for database interaction)
    *   PostgreSQL/MySQL database
    *   RESTful API design
*   **Android App:**
    *   Kotlin
    *   Retrofit (for network requests)
    *   Glide/Picasso (for image loading)
    *   Room (for local caching - optional)
*   **Flutter App (iOS and Android):**
    *   Flutter
    *   `http` package (for network requests)
    *   `cached_network_image` package (for image loading)
*   **Web App:**
    *   React.js
    *   `axios`/`fetch` (for API calls)
    *   State management (Redux/Context API)
*   **Containerization:** Docker (for backend, frontend, and database)
*   **Orchestration:** Kubernetes
*   **CI/CD:** Jenkins (including Docker image building, pushing to a Docker registry, and Kubernetes deployments)
*   **Version Control:** Git

**Development Focus:**

*   **Backend:**
    *   User authentication (JWT)
    *   Post management
    *   Follow/unfollow functionality
    *   Feed generation
*   **Android App:**
    *   User authentication
    *   Post creation (camera integration)
    *   Feed display
    *   User profiles
    *   Push notifications (FCM)
*   **Flutter App:**
    *   Cross-platform development (iOS and Android)
    *   User authentication
    *   Post creation
    *   Feed display
    *   User profiles
*   **Web App:**
    *   User authentication
    *   Post creation
    *   Feed display
    *   User profile management
    *   Advanced post filtering and sorting
*   **CI/CD:** Automating the entire software delivery pipeline, from code commit to deployment in Kubernetes.

**Deployment Environment:**

The application will be deployed and managed within a Kubernetes cluster, leveraging Docker containers for consistent environments and scalability.

**CI/CD Pipeline (Jenkins):**

The Jenkins pipeline will automate the following steps:

1.  Code commit to Git repository.
2.  Backend and frontend build process.
3.  Docker image creation and push to a Docker registry.
4.  Deployment to the Kubernetes cluster.
