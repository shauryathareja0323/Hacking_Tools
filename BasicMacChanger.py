import subprocess
import time

a=input("Enter the desired mac address:")
b=input("Enter the name of the network:")


print(f"[+] changing mac address for {b}")
print("...")
time.sleep(3)

subprocess.call(f"ifconfig {b} down", shell=True)
subprocess.call(f"ifconfig {b} hw ether {a}",shell=True)
subprocess.call(f"ifconfig {b} up",shell=True)
