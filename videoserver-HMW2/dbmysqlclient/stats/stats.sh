#!/bin/busybox ash

FILE_OUTPUT=/stats/stats_logs/stats.txt

timestamp(){
date
}
 
echo "                 __  __  __   __  ___    ___    _        ___   _____     _     _____   ___ " >> $FILE_OUTPUT
echo "                |  \/  | \ \ / / / __|  / _ \  | |      / __| |_   _|   /_\   |_   _| / __|" >> $FILE_OUTPUT
echo "                | |\/| |  \ V /  \__ \ | (_) | | |__    \__ \   | |    / _ \    | |   \__ \\" >> $FILE_OUTPUT
echo "                |_|  |_|   |_|   |___/  \__\_\ |____|   |___/   |_|   /_/ \_\   |_|   |___/" >> $FILE_OUTPUT
echo "                                                                                           " >> $FILE_OUTPUT

while true 
  do
	
		tstart=$(date +%s%N)
		cstart=$(cat /sys/fs/cgroup/cpuacct/cpuacct.usage)

		sleep 10
		echo "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT
		echo "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT

		echo -e "\n\t(1) Statistic about: Query type- $(timestamp)" >> $FILE_OUTPUT
		echo -e "\t" >> $FILE_OUTPUT
		stat1=$(MYSQL_PWD=password mysql -uroot -h127.0.0.1 -P3306 < /stats/stats1.sql)
		echo -e "$stat1" >> $FILE_OUTPUT

		echo "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT
		echo -e "\n\t(2) Statistic about: Query Latency - $(timestamp)" >> $FILE_OUTPUT
		echo -e "\t" >> $FILE_OUTPUT
		stat2=$(MYSQL_PWD=password mysql -uroot -h127.0.0.1 -P3306 < /stats/stats2.sql)
		echo "$stat2" >> $FILE_OUTPUT

		echo "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT
		echo -e "\n\t(3) Statistic about: Errors Occurred - $(timestamp)" >> $FILE_OUTPUT 
		echo -e "\t" >> $FILE_OUTPUT
		stat3=$(MYSQL_PWD=password mysql -uroot -h127.0.0.1 -P3306 < /stats/stats3.sql)
		echo "$stat3" >> $FILE_OUTPUT

		echo "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT
		echo -e "\n\t(4) Statistic about: Query per second - $(timestamp)" >> $FILE_OUTPUT  
		echo -e "\t" >> $FILE_OUTPUT	
		stat4=$(MYSQL_PWD=password mysql -uroot -h127.0.0.1 -P3306 < /stats/stats4.sql) 
		echo "$stat4" >> $FILE_OUTPUT	

		echo "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT		
		echo -e "\n\t(5) Statistic about: Resources usage - $(timestamp)" >> $FILE_OUTPUT 
		echo -e "\t" >> $FILE_OUTPUT	
		tstop=$(date +%s%N)
		cstop=$(cat /sys/fs/cgroup/cpuacct/cpuacct.usage)

		echo -e "\n\tMysql container CPU usage time" >> $FILE_OUTPUT
		time=$(awk "BEGIN {print ($cstop - $cstart) / ($tstop - $tstart) * 100}")
		echo -e "\t $time %" >> $FILE_OUTPUT

		echo -e "\n\tMysql memory usage" >> $FILE_OUTPUT
		mem=$(cat /sys/fs/cgroup/memory/memory.usage_in_bytes  | awk '{byte =$1 /1024/1024; print byte " MB" }')
		echo -e "\t $mem" >> $FILE_OUTPUT
		
		echo -e "--------------------------------------------------------------------------------------------------------------------------------------" >> $FILE_OUTPUT
		echo -e "\n (6-7) Statistic about: Payload Size input/output  - $(timestamp)" >> $FILE_OUTPUT
		echo -e "\t" >> $FILE_OUTPUT        
		echo -e "MB received : \t MB sent :" >> $FILE_OUTPUT
		MYSQL_PWD=password mysql -N -uroot -h127.0.0.1 -P3306 < /stats/stats6_7.sql | awk '{ORS=" "; byte=$1 /1024/1024; print byte " MB \t"}' >> $FILE_OUTPUT

done

