# 🎬 Movie Watchlist App

## Real-World Problem

Nowadays, movie lovers struggle to keep track of films they have watched or plan to watch.
With the vast number of movies released every year, users often forget titles or lose track of interesting movies they come across.

The **Movie Watchlist App** solves this problem by allowing users to search for movies, view movie details, and add movies to their personal watchlist.
It provides a simple and interactive desktop interface built using **Java Swing**, enabling users to explore movies using **The Movie Database (TMDB) API** and manage their own list — all in one place.

---

## Objectives

* Allow users to search for movies by name
* Fetch and display movie data (title, release date, overview, and poster) using the TMDB API
* Enable users to add and view their favorite movies in a watchlist
* Provide a clean and user-friendly graphical interface using Java Swing
* Implement multithreading for live features (e.g., digital clock)

---

## Scope of the Project

### Included Features

* Movie search using TMDB API
* Display of movie posters, titles, and details
* Add movies to a personal watchlist
* Simple navigation buttons (Home, Watchlist, etc.)
* Live digital clock using Java multithreading

### Limitations

* Does not allow video streaming (only movie information)
* Watchlist is not permanently stored (in-memory only)
* Requires an internet connection to fetch data from the API

---

## Object-Oriented Programming (OOP) Concepts Used

| OOP Concept       | How It Is Applied                                                                                         |
| ----------------- | --------------------------------------------------------------------------------------------------------- |
| **Encapsulation** | Each class (e.g., Movie, Config) contains its own data and methods, keeping implementation details hidden |
| **Abstraction**   | Config class hides API complexity and provides simple methods like `searchMovies(String name)`            |
| **Inheritance**   | RoundedButton and RoundedTextField extend Swing components (JButton, JTextField) to customize appearance  |
| **Polymorphism**  | Overriding `paintComponent()` to change how components are displayed                                      |

---

## Technologies Used

* Java
* Java Swing (GUI)
* TMDB API
* Object-Oriented Programming (OOP)
* Multithreading

---

## Screenshots


<img width="600" height="593" alt="WhatsApp Image 2026-04-28 at 6 20 24 PM" src="https://github.com/user-attachments/assets/2154b56a-32eb-4301-854a-29ba56bb7200" />
<img width="600" height="613" alt="WhatsApp Image 2026-04-28 at 6 20 23 PM" src="https://github.com/user-attachments/assets/dfb498bb-ced6-435b-b63d-d55f2689d27e" />
<img width="600" height="673" alt="WhatsApp Image 2026-04-28 at 6 20 23 PM (1)" src="https://github.com/user-attachments/assets/2dc9fa08-468c-43ad-b1b3-99d7180c2580" />
<img width="600" height="593" alt="WhatsApp Image 2026-04-28 at 6 20 22 PM" src="https://github.com/user-attachments/assets/19499a15-c95f-43ab-ba02-2e107893566c" />



## Future Improvements

* Save the watchlist to a database or file
* Add a user login system
* Enhance the user interface with themes or animations
* Improve performance and data storage

---

## Conclusion

This project demonstrates how **Java Swing** can be used to create a modern desktop application that integrates APIs and Object-Oriented Programming principles.
The Movie Watchlist App allows users to efficiently browse and organize movies they wish to watch through an interactive graphical interface.

---

## Author

**Sihath Sethmika**
