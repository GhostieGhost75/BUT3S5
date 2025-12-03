from statistics import median

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

plt.figure(figsize=(16,2))
plt.margins(0, 0)

#data = pd.read_csv('pi_4032000+8_strong.txt', sep=" ", header=None) #strong pi
#plt.plot([1,8], [1,8], color='blue', linestyle='--', linewidth=2, label='Linear')
#data = pd.read_csv('pi_4032000+8_weak.txt', sep=" ", header=None) #weak pi
#plt.plot([1,8], [1,1], color='blue', linestyle='--', linewidth=2, label='Linear')
#data = pd.read_csv('assign_1000000+8_strong.txt', sep=" ", header=None) #strong assignment
#plt.plot([1,8], [1,8], color='blue', linestyle='--', linewidth=2, label='Linear')
#data = pd.read_csv('assign_1000000+8_weak.txt', sep=" ", header=None) #weak assignment
#plt.plot([1,8], [1,1], color='blue', linestyle='--', linewidth=2, label='Linear')

data = pd.read_csv('pi_4032000+16_weak.txt', sep=" ", header=None) #weak assignment
plt.plot([1,16], [1,1], color='blue', linestyle='--', linewidth=2, label='Linear')

data.columns = ["throws", "proc", "ms"]

medians = [0] * max(list(data['proc']))
speedups = [0] * max(list(data['proc']))
coordinates = [[0,0] for i in range(max(list(data['proc'])))]

for i in range(len(medians)) :
    medians[i] = median(list(data.loc[data['proc'] == i+1].ms))
    speedups[i] = medians[0]/medians[i]
plt.plot(list(range(1,17)), speedups, marker='o', color='red', label='Speedup')


print(speedups)
#plt.yticks(np.arange(0, 9, 1))
plt.yticks(np.arange(0, 2, 0.4))
plt.ylabel('Sp', rotation=0)
plt.xlabel('p')
plt.legend()

#plt.title("Scalabilité forte - Pi.java")
#plt.savefig('strong_pi.png', bbox_inches='tight')
#plt.title("Scalabilité faible - Pi.java")
#plt.savefig('weak_pi.png', bbox_inches='tight')
#plt.title("Scalabilité forte - Assignment102.java")
#plt.savefig('strong_assign.png', bbox_inches='tight')
plt.title("Scalabilité faible - Assignment102.java")
#plt.savefig('weak_assign.png', bbox_inches='tight')
plt.show()
