PawCloud
PawCloud is an application designed to improve the management of small-scale veterinary clinics. This project aims to provide an affordable and efficient solution for clinics to optimize their resources without the high costs associated with more complex systems.

Table of Contents
Overview
Features
Technologies Used
Installation
Usage
Development
Contributing
License
Overview
PawCloud is developed to streamline the administrative and operational processes of veterinary clinics. It offers functionalities such as appointment scheduling, medical records management, inventory control, and billing, all through an intuitive and user-friendly interface. The application ensures secure and centralized access to all crucial information via cloud storage, enhancing operational efficiency and customer service quality.

Features
User Authentication: Secure user registration and login.
Client and Pet Management: Detailed records for clients and their pets, including medical histories and vaccination records.
Appointment Scheduling: Easy scheduling and tracking of veterinary appointments.
Inventory Management: Efficient tracking of medications, food supplies, and other inventory items.
Billing and Invoicing: Automated generation and management of receipts and invoices.
Scalability: Designed to grow with your clinic, allowing the addition of multiple users, features, and storage as needed.
Technologies Used
Java: Core programming language for application logic.
Swing: Java library for building graphical user interfaces.
JDBC: Java Database Connectivity for database interactions.
MySQL: Database management system.
AWS: Cloud storage and services.
BCrypt: For secure password hashing.
Maven: Project management and dependency management.
GitHub: Version control system.
Excalidraw: Tool for designing user interfaces.
DBeaver: Database management tool.
Installation
Clone the Repository:

bash
Copiar código
git clone https://github.com/PedroAP91/PAWCLOUD.git
cd PAWCLOUD
Install Dependencies:
Ensure you have Java and Maven installed. Install the required libraries using Maven:

bash
Copiar código
mvn install
Setup Database:

Configure your MySQL database.
Update the database connection details in the application configuration.
Run the Application:

bash
Copiar código
mvn exec:java
Usage
Login: Start the application and log in using your credentials.
Dashboard: Access various panels for managing clients, pets, appointments, inventory, and billing.
Settings: Configure user accounts and other administrative settings.
Development
Methodology
The project followed an incremental development approach, dividing requirements into small functional units. Each unit was designed, implemented, tested, and evaluated individually.

Branching Strategy
Development was managed using GitHub with feature branches. Each new feature or bug fix was implemented in its branch and merged into the main branch after review and testing.

Tools
Eclipse IDE: Used for coding and debugging.
Excalidraw: For designing UI mockups.
WindowBuilder: Eclipse plugin for designing GUI components.
DBeaver: For managing the database.
Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure your code follows the existing style and includes appropriate tests.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Feel free to explore, use, and contribute to PawCloud. Together, we can improve the management and efficiency of veterinary clinics, ensuring better care for pets and streamlined operations for veterinary professionals.
