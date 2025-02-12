echo "Transfering data to the playground node."
sshpass -f password ssh sd207@l040101-ws05.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws05.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirPlayground.zip sd207@l040101-ws05.ua.pt:PullTheRope

echo "Decompressing data sent to the playground node."
sshpass -f password ssh sd207@l040101-ws05.ua.pt 'cd PullTheRope ; unzip -uq dirPlayground.zip'

echo "Executing program at the server playground."

sshpass -f password ssh sd207@l040101-ws05.ua.pt 'cd PullTheRope/dirPlayground ; java ServerSide.main.ServerPlayground 22264 l040101-ws01.ua.pt 22260'

echo "playground server shutdown"