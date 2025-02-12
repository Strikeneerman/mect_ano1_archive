echo "Transfering data to the referee site node."
sshpass -f password ssh sd207@l040101-ws10.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws10.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirRefereeSite.zip sd207@l040101-ws10.ua.pt:PullTheRope

echo "Decompressing data sent to the general rrepository node."
sshpass -f password ssh sd207@l040101-ws10.ua.pt 'cd PullTheRope ; unzip -uq dirRefereeSite.zip'

echo "Executing program at the server referee site."
sshpass -f password ssh sd207@l040101-ws10.ua.pt 'cd PullTheRope/dirRefereeSite ; java ServerSide.main.ServerRefereeSite 22261 l040101-ws01.ua.pt 22260'

echo "Referee site server shutdown"