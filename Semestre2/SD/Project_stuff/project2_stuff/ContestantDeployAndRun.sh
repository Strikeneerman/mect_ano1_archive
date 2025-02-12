echo "Transfering data to the contestant node."
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirContestant.zip sd207@l040101-ws07.ua.pt:PullTheRope

echo "Decompressing data sent to the contestant node."
sshpass -f password ssh sd207@l040101-ws07.ua.pt 'cd PullTheRope ; unzip -uq dirContestant.zip'

echo "Executing program at the contestant node."

sshpass -f password ssh sd207@l040101-ws07.ua.pt 'cd PullTheRope/dirContestant ; java ClientSide.main.clientContestant l040101-ws09.ua.pt 22262 l040101-ws05.ua.pt 22264'

echo "Contestant client shutdown"