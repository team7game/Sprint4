import sys
import socket
import random
import time

bufferSize  = 1024
serverAddressPort   = ("127.0.0.1", 7501)

redPlayers = []
greenPlayers = []
counter = sys.argv[len(sys.argv)-1]

a = 1

#edited the python generator to work for any number of team members

while a <= len(sys.argv):

    if(a < (len(sys.argv))/2):
        redPlayers.append(sys.argv[a])
    elif((a >= (len(sys.argv))/2)) and (a < len(sys.argv)-1):
        greenPlayers.append(sys.argv[a])
    
    a = a + 1

# Create datagram socket
UDPClientSocketTransmit = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

# counter number of events, random player and order
i = 0
while i < int(counter):

    redPlayer = redPlayers[random.randint(0, len(redPlayers)-1)]
    greenPlayer = greenPlayers[random.randint(0, len(greenPlayers)-1)]

    if random.randint(1,2) == 1:
        message = str(redPlayer) + ":" + str(greenPlayer)
    else:
        message = str(greenPlayer) + ":" + str(redPlayer)

    

    print(message)
    i+=1
    UDPClientSocketTransmit.sendto(str.encode(str(message)), serverAddressPort)
    time.sleep(random.randint(1,3))

UDPClientSocketTransmit.sendto(str.encode("bye"), serverAddressPort)
print("program complete")