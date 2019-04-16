cd C:\projects\greenstar\mt\371793-Hackathon-Backend
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\371793-Hackathon-Discovery
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\Hackathon-Gateway
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\Hackathon-PerformanceDataService
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\371793-Hackathon-SchoolService
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\Hackathon-SecurityService
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\371793-Hackathon-StudentService
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd C:\projects\greenstar\mt\371793-Hackathon-Zipkin
call git.exe pull --progress -v --no-rebase "origin"
git config --global credential.helper store
call mvn clean install -Dmaven.test.skip=true

cd c:\Users\Admin\Desktop
