echo "Transfering data to the coach node."
sshpass -f password ssh sd207@l040101-ws06.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws06.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirCoach.zip sd207@l040101-ws06.ua.pt:PullTheRope

echo "Decompressing data sent to the coach node."
sshpass -f password ssh sd207@l040101-ws06.ua.pt 'cd PullTheRope ; unzip -uq dirCoach.zip'

echo "Executing program at the coach node."

sshpass -f password ssh sd207@l040101-ws06.ua.pt 'cd PullTheRope/dirCoach ; java ClientSide.main.clientCoach l040101-ws09.ua.pt 22262 l040101-ws05.ua.pt 22264 l040101-ws10.ua.pt 22261'

echo "Coach client shutdown"