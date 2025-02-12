function [bestSol,bestObjective,noCycles,avObjective] = MultiHillRandomAlgorithm(nNodes,Links,T,sP,nSP,timeLimit)
    t= tic;
    nFlows= size(T,1);
    bestObjective= inf;
    noCycles= 0;
    aux= 0;
    while toc(t) < timeLimit
        sol= zeros(1,nFlows);
        for f= 1:nFlows
            sol(f)= randi(nSP(f));
        end
        
        Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
        load= max(max(Loads(:,3:4)));
        
        [sol,load]= MHR(nFlows,nSP,nNodes,Links,T,sP,sol,load);

        noCycles= noCycles+1;
        aux= aux+load;
        if load<bestObjective
            bestSol= sol;
            bestObjective= load;
        end
    end
    avObjective= aux/noCycles;
end

function [sol,load] = MHR(nFlows,nSP,nNodes,Links,T,sP,sol,load)
        
        BestLoad = load;
        BestSol = sol;
        
        improved = true;

        while improved
            for flow = 1:nFlows
                for path = 1:nSP(flow)
                    if path ~= sol(flow)

                        auxSol = sol;
                        auxSol(flow) = path;

                        Loads= calculateLinkLoads(nNodes,Links,T,sP,auxSol);
                        auxLoad= max(max(Loads(:,3:4)));

                        if auxLoad < BestLoad
                            BestLoad = auxLoad;
                            BestSol = auxSol;
                       
                        end
                    end
                end
            end

            if BestLoad<load
                load= BestLoad;
                sol= BestSol;
            else
                improved = false;
            end
        end
end