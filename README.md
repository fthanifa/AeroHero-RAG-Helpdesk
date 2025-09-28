# ✈️ AeroHero – AI-Powered Airline Support System

AeroHero is a **JavaFX-based airline helpdesk application** that integrates **RAG (Retrieval-Augmented Generation)** and **LangChain4j** to provide passengers with fast, accurate, and context-aware support.  
It features a **desktop UI built with JavaFX + FXML** and connects to intelligent AI services for handling FAQs, ticketing details, baggage policies, refund info, and more.

---

## Features
- 🖥️ **Interactive JavaFX Interface** – clean, responsive UI with FXML + CSS styling
- ☁️ **Expandable Backend** – designed for future integration with cloud APIs and airline databases

### AI Chatbot
- Powered by **LangChain4j + OpenAI**  
- Uses **RAG (Retrieval-Augmented Generation)** for context-aware responses  
- Answers based on `.txt` knowledge files (flight info, baggage, refund policy, etc.) 

### User Management (Login & Register)
- Register as either Passenger or Admin  
- Role-based login redirects to different dashboards  
- Credentials stored in `users.txt` (with hardcoded admin support)

### Homepage / Landing Page
- Provides an overview of the AeroHero airline support system  
- Highlights main features like ticketing, tracking, AI chatbot, and dashboards  
- Guides users to log in or register

### Ticketing System
- Passengers can:
  - Submit tickets with a title, category, priority, and message
  - View a list of previously submitted tickets with *color-coded status*
- Admins can:
  - View all tickets with statistics (Total, Open, In Progress, Resolved)
  - Update ticket priority and status in real-time
  - Ticket data is stored in `tickets.txt` file

### Flight & Baggage Tracking
- Search baggage or flight through baggage ID or flight number
- Pulls data from `track.txt`
- Displays tracking info (baggage ID, flight status, pickup time)
- Shows randomized travel tips on each query

### Knowledge Base / FAQ System
- Searchable FAQ for both passengers and admins  
- **Passengers:** Can search FAQ to get instant answers about flight info, baggage rules, refunds, etc.  
- **Admins:** Can add, delete, and search FAQ entries to keep the knowledge base up-to-date 
- Uses `.txt` files for structured storage and integration with AI chatbot

---

## 🛠 Tech Stack

- **Language:** Java 23
- **UI:** JavaFX 24 + FXML (via SceneBuilder) + CSS
- **AI Integration:** LangChain4j 1.0.1 (Chatbot module), OpenAI (gpt 4o)
- **Data Storage:** Plain Text File Storage, CSV and Binary File Storage (`tickets.txt`, `track.txt`, `faq_data.txt`, 'users.dar' etc.)

---

## 🚀 Getting Started

### Prerequisites
- Java 17+  
- Maven 3.9+  

### How to Run

1. Make sure JavaFX is configured in your IDE (NetBeans, IntelliJ, etc.)
2. Open the `Main.java` class and right click and run
3. The app will launch with the homescreen

### Build and run with Maven
- mvn clean javafx:run

### Run Locally
- Clone the repository:
- git clone https://github.com/fthanifa/AeroHero-RAG-Helpdesk.git
- cd AeroHero-RAG-Helpdesk

---

## Notes

- Admin login is hardcoded:  
**Email**: `admin@aerohero.com`  
**Password**: `admin123`
- Ticket and user data are stored in `.txt` files for simplicity.

---

Made with ❤️ by AeroHero Team – Fitri, Ali, Dhika & Anna

