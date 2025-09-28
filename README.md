# AeroHero Support System

A complete JavaFX-based airline support system for passengers and staff. This desktop application allows users to register, log in, submit support tickets, track baggage or flights, and chat with an AI assistant. Admins can manage tickets and expand the knowledge base.

---

## Features

### User Management (Login & Register)
- Register as either Passenger or Admin
- User credentials stored in plain `.txt` file (`users.txt`)
- Hardcoded admin support
- Role-based login redirects to different screens

### Ticketing System
- Passengers can:
  - Submit tickets with a title, category, priority, and message
  - View list of previously submitted tickets with color-coded status
- Admins can:
  - View all tickets with statistics (Total, Open, In Progress, Resolved)
  - Update ticket priority and status in real-time
- Ticket data stored in `tickets.txt` file

### Flight & Baggage Tracking
- Input baggage ID or flight number
- Pulls data from `track.txt`
- Displays tracking info (baggage ID, flight status, pickup time)
- Shows randomized travel tips on each query

### AI Chatbot (FAQ Assistant)
- Built with LangChain4j + OpenAI
- Answers questions using stored `.txt` knowledge files
- Context-aware RAG (Retrieval-Augmented Generation)
- Styled in custom AeroHero UI (dark mode, Poppins font, 1280x800)

---

## Technologies Used

- Java 23
- JavaFX 24 + FXML (via SceneBuilder)
- LangChain4j 1.0.1 (Chatbot module)
- Plain Text File Storage, CSV Storage and Binary File Storage (`tickets.txt`, `track.txt`, `faq_data.txt`, 'users.dar' etc.)

---

## How to Run

1. Make sure JavaFX is configured in your IDE (NetBeans, IntelliJ, etc.)
2. Set the VM options:
3. Open the `Main.java` class and run the application.
4. The app will launch with the login screen.

---

## Notes

- Admin login is hardcoded:  
**Email**: `admin@aerohero.com`  
**Password**: `admin123`
- Ticket and user data are stored in `.txt` files for simplicity.
- Chatbot requires an OpenAI API key, managed inside `ApiKeys.java`

---

Made with ❤️ by AeroHero Team – Ali, Dhika, Fitri, Anna

