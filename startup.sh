ADMIN_PID=admin_pid
APP_PID=app_pid
# 启动admin
if [ -f "$ADMIN_PID" ] && kill -0 $(cat "$ADMIN_PID"); then
	echo "=========进程在运行。。。准备杀死"
	PID="$(cat "$ADMIN_PID")"
	kill -9 $PID
	rm -rf "$ADMIN_PID"
	echo "=========进程kill完成,准备启动==========="
fi
nohup java -jar -Xms200m -Xmx256m  admin/target/admin-0.0.1.jar >> logs/nohup.out 2>&1 & echo $! > $ADMIN_PID
echo "=========启动完成========="
#启动app
if [ -f "$APP_PID" ] && kill -0 $(cat "$APP_PID"); then
	echo "=========进程在运行。。。准备杀死"
	PID="$(cat "$APP_PID")"
	kill -9 $PID
	rm -rf "$APP_PID"
	echo "=========进程kill完成,准备启动==========="
fi
nohup java -jar -Xms200m -Xmx256m  app/target/app-0.0.1.jar >> logs/nohup.out 2>&1 & echo $! > $APP_PID
echo "=========启动完成========="