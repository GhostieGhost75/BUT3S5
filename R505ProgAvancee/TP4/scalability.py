from statistics import median

import matplotlib.pyplot as plt
import pandas as pd

plt.figure(figsize=(8,8))
plt.plot([1,8], [1,8], color='blue', linestyle='--', linewidth=2, label='Linear')
plt.legend()
#plt.show()


data = pd.read_csv('pi_4032000+8_strong.txt', sep=" ", header=None)
data.columns = ["throws", "proc", "ms"]

medians = [0] * max(list(data['proc']))
speedups = [0] * max(list(data['proc']))
coordinates = [[0,0] for i in range(max(list(data['proc'])))]

for i in range(len(medians)) :
    medians[i] = median(list(data.loc[data['proc'] == i+1].ms))
    speedups[i] = medians[0]/medians[i]
    coordinates[i] = [speedups[i], i+1]

#print(data.loc[data['proc'] == 8].ms)
#print(median(list(data.loc[data['proc'] == 8].ms)))
#print(max(list(data['proc'])))

print(medians)
print(speedups)
print(coordinates)