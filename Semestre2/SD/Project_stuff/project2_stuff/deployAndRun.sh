xterm  -T "General Repository" -hold -e "./GeneralRepoDeployAndRun.sh" &
sleep 1
xterm  -T "Referee Site" -hold -e "./RefereeSiteDeployAndRun.sh" &
xterm  -T "Bench" -hold -e "./BenchDeployAndRun.sh" &
xterm  -T "Playground" -hold -e "./PlaygroundDeployAndRun.sh" &
sleep 1
xterm  -T "Coach" -hold -e "./CoachDeployAndRun.sh" &
xterm  -T "Contestant" -hold -e "./ContestantDeployAndRun.sh" &
xterm  -T "Referee" -hold -e "./RefereeDeployAndRun.sh" 