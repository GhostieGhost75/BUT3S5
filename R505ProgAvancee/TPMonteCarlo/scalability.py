from statistics import median

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

plt.figure(figsize=(8,8))
plt.margins(0, 0)

data = pd.read_csv('socket_16000000x1_weak.txt', sep=" ", header=None) #weak assignment
plt.plot([1,19], [1,9], color='blue', linestyle='--', linewidth=2, label='Linear')

data.columns = ["proc", "throws", "ms", "value"]

cores = [1, 2, 3, 4, 5, 6, 8, 12, 18]

medians = [0] * len(cores)
speedups = [0] * len(cores)
coordinates = [[0,0] for i in range(max(list(data['proc'])))]

for i in range(len(medians)) :
    medians[i] = median(list(data.loc[data['proc'] == cores[i]].ms))
    speedups[i] = medians[0]/medians[i]
plt.plot(cores, speedups, marker='o', color='red', label='Speedup')


print(speedups)
plt.yticks(np.arange(0, 10, 1))
#plt.yticks(np.arange(0, 2, 0.4))
plt.ylabel('Sp', rotation=0)
plt.xlabel('p')
plt.legend()

#plt.title("Scalabilité forte - Pi.java")
#plt.savefig('strong_pi.png', bbox_inches='tight')
#plt.title("Scalabilité faible - Pi.java")
#plt.savefig('weak_pi.png', bbox_inches='tight')
#plt.title("Scalabilité forte - Assignment102.java")
#plt.savefig('strong_assign.png', bbox_inches='tight')
plt.title("Scalabilité forte - JavaSocket")
#plt.savefig('strong_socket.png', bbox_inches='tight')
plt.show()
