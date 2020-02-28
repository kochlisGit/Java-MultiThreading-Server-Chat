# FastChat
This is a simple Team Chat written in Java for a school project. Multiple Clients can connect with a server and send messages.
The Server receives the messages and send them back to all connected clients.

# Overview
Fast Chat is a multi-threaded Server-Client implementation, where multiple clients can connect to a server and communicate all together.

![alt text](https://github.com/kochlisGit/FastChat/blob/master/img0.png)

![alt text](https://github.com/kochlisGit/FastChat/blob/master/img1.png)

# Server
In order for this App to work, there must be a server running in the background, so that clients can connect to.
The server uses the **Machine's Local IP Address** and **Port: 4**, so clients must know this IP Address in order to connect.

The server is **Mutli-Threaded** A thread listens to connections and a ClientHandler handles the connections.
The way it Works is simple. The server starts a Listener thread that waits clients to connect. Every time a client connects then
the server passes the client's socket to ClientHandler. ClientHandler controls the ServerReceiver and the ServerSender.
ServerReceiver listens to messages coming from clients and puts them in a queue.
ServerSender takes messages from the queue and sends them to every connected client.

# Clients
Clients can connect to server via the Application's GUI. Clients can send message and receive the Name of the client who send the message,
the message and the time the message was sent. Clients can connect and disconnect as long as the server is running.

# Features
1. A Very simple and easy to use Graphical User Interface (GUI)
2. A Multi-threaded server that automatically configures the server's settings.
3. All clients are connected.
4. The chat can be exported as PDF! The file is created in the user's **Download"" folder as chat.pdf

# How to Run
This app was written with jdk-11.0.2. Also it uses javafx-sdk-11.0.2.
Additionally, it uses Apache PDFBox library to convert text to PDF. This library is uploaded in the project's folder.

-- Steps

1. Download jdk-11.0.2
[Download here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

2. Download javafx-sdk-11.0.2
[Download here](https://gluonhq.com/products/javafx/)

3. Import the app to Eclipse or Intellij.
4. Go to Project Structure and add jdk-11.0.2 and javafx-sdk-11.0.2.
This link can help you setup javafx-sdk [Documentation](https://openjfx.io/openjfx-docs/)

5. Add Apache PDFBox library to the project.
6. Compile and Run Server.java
**javac servers/Server.java**

**java servers/Server**

7. Run FastChatMain.java


