#! /bin/bash
# ::System Monitor v1.0:: (optimized for Ubuntu systems)
# DESCRIPTION               
# This simple bash script sends via serial port informations about system.
# REQUIREMENTS
# Before run, install sysstat and bwm-ng:
# sudo apt-get install sysstat bwm-ng
# AUTHOR 
# Stefano Manni | http://www.stefanomanni.it/arduino | ::FREE SOFTWARE::

netDev=""
diskPath=""
tty=""

#Parse input parameters
for i in $*
do
	case $i in
    	--net=*)
		netDev=`echo $i | sed 's/[-a-zA-Z0-9]*=//'`
		;;
    	--disk=*)
		diskPath=`echo $i | sed 's/[-a-zA-Z0-9]*=//'`	
		;;
	--tty=*)
		tty=`echo $i | sed 's/[-a-zA-Z0-9]*=//'`	
		;;
		
    	-h)
    		clear
                echo "::System Monitor v1.0::"
                echo ""    
                echo "AUTHOR: Stefano Manni | http://www.stefanomanni.it/arduino | ::FREE SOFTWARE::"            
                echo ""                                   
                echo "DESCRIPTION:"                
                echo "This simple bash script sends via serial port informations about system:"
                echo "* Uptime"
                echo "* Cpu percent usage"
                echo "* Memory used"  
                echo "* Download rate"                 
                echo "* Cpu temperature"                                                                                             
                echo "These data are packed in a single long string and then parsed and interpreted by Arduino."                
                echo ""
                echo "REQUIREMENTS:"      
                echo "Before run, install sysstat and bwm-ng. On ubuntu linux:"      
                echo "sudo apt-get install sysstat bwm-ng"            
                echo ""                                           
                echo "PARAMETERS:"                                
      		echo "--net= <network device eg eth0>"
     		echo "--disk= <disk path eg /dev/sda1>"
		echo "--tty= <serial device eg /dev/ttyUSB0>"
                echo ""                                           
                echo "EXAMPLES:"    
                echo "./systemMonitor.sh --net=eth0 --disk=/dev/sda1 --tty=/dev/ttyUSB0"		
	        exit
	        ;;

  	esac
done

if [ "$netDev" == "" ] && [ "$diskPath" == "" ] && [ "$tty" == "" ]
then  
  echo "Usage: -h for help"
  exit
fi

if [ "$netDev" == "" ]
then
  echo "Usage: --net= <network device to be analyzed eg eth0>"
  exit
fi

if [ "$diskPath" == "" ]
then
  echo "Usage: --disk= <disk path eg /dev/sda1>"
  exit
fi

if [ "$tty" == "" ]
then
  echo "Usage: --tty= <serial device eg /dev/ttyUSB0>"
  exit
fi


#Send data
clear
echo -n "Sending data to Arduino"

while true; 
do
	UPTIME=$(uptime | awk '{ print $3 }' | cut -d, -f1)					
	CPU=$(mpstat -P ALL 1 1 | grep Average | grep all | awk '{print $3"%"}')
	RAM=$(free -mo | grep Mem: | awk '{ print $3"MB" }')
	NETdown=$(bwm-ng -o plain -c 1 | grep $netDev | awk '{print $2"kB/s"}')
	FS=$(df -hT | grep $diskPath | awk '{print $6}')
	TEMP=$(cat /proc/acpi/thermal_zone/TZS0/temperature | awk '{print $2"C"}')
	
        echo $UPTIME@$CPU@$RAM@$NETdown@$FS@$TEMP > $tty
        echo -n "."
 
done



