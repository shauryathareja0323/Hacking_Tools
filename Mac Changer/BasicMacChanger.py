import subprocess
import argparse
import time
import re
import sys

def get_arguments():
    parser = argparse.ArgumentParser()
    parser.add_argument("-i", "--interface", dest="interface", help="Interface to change it's mac.")
    parser.add_argument("-m", "--mac", dest="mac", help="New Mac.")
    options = parser.parse_args()
    if not options.mac:
        parser.error("[-] Please specify a mac address, use --help for more info.")

    if not options.interface:
        parser.error("[-] Please specify a mac address, use --help for more info.")

    return options

def mac_changer(interface, mac):
    print(f"[+] changing mac address for {interface} to {mac}")
    time.sleep(0.5)
    subprocess.call(f"ifconfig {interface} hw ether {mac}", shell=True)

def loading_animation(duration):
    print('[+] Changing the Mac Address')
    for i in range(duration):
        time.sleep(.5)
        sys.stdout.write('.')
        sys.stdout.flush()

def print_mac(interface):
    ifconfig_result = subprocess.check_output(["ifconfig",interface])
    ifconfig_result_str = str(ifconfig_result)

    mac_add_search_result = re.findall("\w\w:\w\w:\w\w:\w\w:\w\w:\w\w", ifconfig_result_str)

    if mac_add_search_result:
        return mac_add_search_result[0]
    else:
        print("[-] Could not read mac address.")



options=get_arguments()
print_mac(options.interface)
print("Current Mac =",print_mac(options.interface))
mac_changer(options.interface, options.mac)
print_mac(options.interface)
loading_animation(10)
if print_mac(options.interface)==options.mac:
    print("\n[+] Mac address successfully changed to",print_mac(options.interface))
else:
    print("[-] Could not change the mac address")
