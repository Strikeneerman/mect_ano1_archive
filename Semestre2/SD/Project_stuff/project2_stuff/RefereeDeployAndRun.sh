echo "Transfering data to the referee node."
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirReferee.zip sd207@l040101-ws08.ua.pt:PullTheRope

echo "Decompressing data sent to the referee node."
sshpass -f password ssh sd207@l040101-ws08.ua.pt 'cd PullTheRope ; unzip -uq dirReferee.zip'

echo "Executing program at the referee node."

sshpass -f password ssh sd207@l040101-ws08.ua.pt 'cd PullTheRope/dirReferee ; java ClientSide.main.clientReferee l040101-ws09.ua.pt 22262 l040101-ws05.ua.pt 22264 l040101-ws10.ua.pt 22261  l040101-ws01.ua.pt 22260'

echo "Referee client shutdown"