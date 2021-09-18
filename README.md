# Chatting-Application
A Java project that creates a chatting app (like Messenger) between 2 people. One is the server and the other is a client.

This is a java project that creates a basic chatting application like Messenger. You have 2 classes, the Server and the Client.

With the use of ServerSocket, Socket, DataInputStream and DataOutputStream, the 2 Classes connect with each other. First you run the Server.java, so that a server socket gets created. After that, you run the Client.java, and a connection will be made between the client and the server.

Then every time someone writes a message in the textField and clicks send, the message will be displayed both in the sender and the receiver. Underneath the message, is the time of when it was send. Also, the message will first appear to the sender and the receiver will see it 2 seconds after it was send.

If you want to close the frames, all you have to do is click the arrow button left of the user avatar.

Whenever someone is typing, the "Active Now" label changes to "Typing..." and after 2 seconds of no typing, it turns back to "Active Now".

For more information about how the code works, there are lots of comments in the .java files to help you understand everything and follow along with ease!
