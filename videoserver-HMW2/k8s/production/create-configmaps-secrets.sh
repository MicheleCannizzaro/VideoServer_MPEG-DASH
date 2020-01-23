kubectl create configmap vms-env-file --from-env-file=./videomanagementservice/vms-env-file.properties --save-config
kubectl get configmap vms-env-file -o yaml > videomanagementservice/vms-env-file.yml

kubectl create configmap vs-db-env-file --from-env-file=./videomanagementservice/vs-db-env-file.properties --save-config
kubectl get configmap vs-db-env-file -o yaml > videomanagementservice/vs-db-env-file.yml

kubectl create configmap vs-db-client-env-file --from-env-file=./videomanagementservice/vs-db-client-env-file.properties --save-config
kubectl get configmap vs-db-client-env-file -o yaml > videomanagementservice/vs-db-client-env-file.yml

kubectl create configmap vps-env-file --from-env-file=./videoprocessingservice/vps-env-file.properties --save-config
kubectl get configmap vps-env-file -o yaml > videoprocessingservice/vps-env-file.yml

kubectl create secret generic vms-secret-file --from-env-file=./videomanagementservice/vms-secret-file.properties --save-config
kubectl get secret vms-secret-file -o yaml > videomanagementservice/vms-secret-file.yml

kubectl create secret generic vs-db-client-secret-file --from-env-file=./videomanagementservice/vs-db-client-secret-file.properties --save-config
kubectl get secret vs-db-client-secret-file -o yaml > videomanagementservice/vs-db-client-secret-file.yml

kubectl create secret generic vs-db-secret-file --from-env-file=./videomanagementservice/vs-db-secret-file.properties --save-config
kubectl get secret vs-db-secret-file -o yaml > videomanagementservice/vs-db-secret-file.yml
