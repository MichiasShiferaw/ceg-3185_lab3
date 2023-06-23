# CEG Lab : 3 Socket Programming & the IPv4

This lab demonstrates the importance of IP protocol; with the use of Network Socket programming. 

&nbsp;

## Project Objectives

- &check; Implement two programs, a client and a server and two func (methods), one to encode (at the client side), one to decode (at the server side)
- The Client: [PacketSender.java](#class-description)
    - &check; Reads the data from user 
    - &check; Sends the encoded stream to the server through socket 
- The Server: [PacketReceiver.java](#class-description)
    - &check; Acknowledges the client that the encoded stream has been received
    - &check; Decodes the stream and prints it on the screen


## Class Description

| Class     |  Description                |
| :-------- | :------------------------- |
| [`PacketSender`](#client) | The user will decide to use default or provide custom data to represent the payload and the source/destination IP addresses. Afterwards, the class will initiate a request for a connection to the server with a specified IP address and port number. Afterwards, the data is converted into a hex-format datagram and sent to the server for decoding. |
| [`PacketReciever`](#server) | Waits for incoming client/sender requests by listening to a specified port (4999), once accepted, the server performs a decoding function involving the checksum function to determine if there are no errors. If no errors are found, the program unveils the datagram including the source IP and message/payload as well as their respective length in bytes. |


&nbsp;


## Usage/Examples

&nbsp;

To run this project:

&nbsp;

Open up two seperate terminals

&nbsp;

### Server
In one terminal run;

```bash
javac PacketReceiver.java
```
to read and compile class file.

&nbsp;

```bash
java PacketReceiver
```
to execute the server side component.

&nbsp;

### Client
In the other terminal run;
```bash
javac PacketSender.java
```
to read and compile class file.

&nbsp;

```bash
java PacketSender -server {DEST_IP} -payload {"custom_payload"}
```

OR 

For default outputs
```bash
java PacketSender 
```

to execute the client side

&nbsp;

## Demo

![Default Data ](https://github.com/MichiasShiferaw/ceg-3185_lab3/blob/main/Output/LabOutput1.png)

![Custom Data](https://github.com/MichiasShiferaw/ceg-3185_lab3/blob/main/Output/LabOutput2.png)

&nbsp;

## Directory Structure


```bash
├── Output
│   ├── Output1.png 
│   └── Output2.png
├── PacketReciever.java
├── PacketSender.java
└── README.md
```

&nbsp;

## Authors

- [@michiasshiferaw](https://www.github.com/michiasshiferaw)
- [@teodoravuk22](https://github.com/teodoravuk22)

&nbsp;

## References

https://docs.oracle.com/javase/tutorial/networking/sockets/cIientServer.html 