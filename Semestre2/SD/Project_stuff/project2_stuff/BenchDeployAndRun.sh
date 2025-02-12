echo "Transfering data to the bench node."
sshpass -f password ssh sd207@l040101-ws09.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws09.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirBench.zip sd207@l040101-ws09.ua.pt:PullTheRope

echo "Decompressing data sent to the bench node."
sshpass -f password ssh sd207@l040101-ws09.ua.pt 'cd PullTheRope ; unzip -uq dirBench.zip'

echo "Executing program at the server bench."

sshpass -f password ssh sd207@l040101-ws09.ua.pt 'cd PullTheRope/dirBench ; java ServerSide.main.ServerBench 22262 l040101-ws01.ua.pt 22260'

echo "Bench server shutdown"