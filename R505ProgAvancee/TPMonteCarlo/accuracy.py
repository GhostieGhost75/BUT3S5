import math
from statistics import median

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

plt.figure(figsize=(8,8))
plt.margins(0, 0)

data = pd.read_csv('pi_1000+8_errors.txt', sep=" ", header=None)
data.columns = ["throws", "procs", "pi"]

medians = [0] * data['throws'].nunique()
speedups = [0] * data['throws'].nunique()
print(data['throws'].unique()[1])
print(list(data.loc[data['throws'] == data['throws'].unique()[1]]))
errors = [data.pi[i]/math.pi for i in range(len(data['pi']))]
print(list(data.loc[data['throws'] == 8000].pi))

plt.scatter(data['throws'], errors)
#plt.yticks(np.arange(0, 9, 1))
#plt.yticks(np.arange(0, 2, 0.4))
plt.ylabel('Sp', rotation=0)
plt.xlabel('p')
plt.legend()

plt.title("Scalabilité faible - Assignment102.java")
plt.savefig('weak_assign.png', bbox_inches='tight')
plt.show()