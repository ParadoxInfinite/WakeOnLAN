# WakeOnLAN
### Mini project for Computer Networks(5th Sem)

### Updated the 2018 Java project with no persistence and light theme to Kotlin with persistent database and a dark (AMOLED) theme.

### Instructions to install :
1. Clone the repository and build in Android Studio **or** Download the release APK.
2. Copy the *WakeOnLAN.apk* to your device and install it.

### Ensure before using the app :
1. Your PC *MUST* support WakeOnLAN. (_Sometimes Motherboards are not compatible, make sure yours is._)
2. Enable WakeOnLAN on BIOS as well as Windows. (_MacOS and Linux have not been tested, if it works for you, do make a pull request updating this README with instructions._)

### Steps to enable WakeOnLAN :
1. In BIOS
    - Usually the option is under Power/Power Management setting. It could be different for different motherboards.
2. In Windows
    1. Right click or press Windows key + X, select 'Device Manager'.
    2. Under 'Network Adapters' right click on your Ethernet Adapter/Controller and select 'Properties'.
    3. Under 'Advanced' tab, scroll down the 'Property' list to find 'Wake on magic Packet' and 'Wake on pattern match', make sure both are enabled.
    4. In the same window, under 'Power Management' tab, make sure 'Allow this device to wake the computer' and 'Only Allowa magic packet to wake the computer' are checked.
    5. That's it for the setup.

### Things to note :
1. Your phone needs to be connected to the same WiFi router that your PC is plugged in to. (Otherwise you will need to setup port forwarding on the router and use public IP instead of local IP)
2. Broadcast IP address of networks are usually 192.168.0.255 *or* 192.168.1.255 as the subnet mask is 255.255.255.0 generally. Read mode about [Broadcast IP](https://en.wikipedia.org/wiki/Broadcast_address). If your subnet mask is different, you might need to adjust your broadcast IP accordingly. To check your subnet mask and actual IP, in a command prompt, type 'ipconfig'.

### Instructions to use :
1. Open the app.
2. Click on the Add button on first use.
3. Enter the broadcast address of your network, **not** the actual IP of the computer you wish to wake.
4. Enter the Physical/MAC address of the computer that you wish to wake.
5. Press the *Add Machine* button to Keep the machine persistent.
6. If there is an error with the length/Format of the MAC Address, you will get the respective error.
7. Press the *Go back* button and select which computer you wish to wake up. This will give either "Magic Packet Sent" or "Magic Packet failed" depending on if the packet was sent successfully or not.

### Issue reporting :
Feel free to report any issues, errors that you are facing in the issues section. If there is an enchancement request, add the appropriate tag.
Issue report must contain version of Android, Phone make and model and the error details.
