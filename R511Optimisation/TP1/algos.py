import re

class Backpack:
    def __init__(self, vals, weights, N, W) :
        self.poids = weights
        self.val = vals
        self.N = N
        self.W = W

    def valeur(self, s: set[int]):
        """Renvoie la valeur d'une solution"""
        val = 0
        for i in s :
            val += self.valeurs[i]
        return val

def lire_bp(file:str) :
    with open('data/'+file) as sac :
        nbrs = re.findall(r'\d+',sac.read())
        vals = [0]*((len(nbrs)-2)//2)
        weights = [0]*((len(nbrs)-2)//2)
        for i in range(2, len(nbrs)-1, 2) :
            vals[(i//2)-1] = int(nbrs[i])
            weights[(i//2)-1] = int(nbrs[i+1])
        return Backpack(vals, weights, int(nbrs[0]), int(nbrs[1]))
         # A REPARER


def glouton(file) :
    bp = lire_bp(file)
    eff = [bp.val[i]/bp.poids[i] for i in range(bp.N)]
    items = list(range(bp.N))
    items.sort(key = lambda i:eff[i], reverse = True)
    val = 0
    w = bp.W
    for i in items :
        if bp.poids[i] <= w :
            val += bp.val[i]
            w -= bp.poids[i]
    return val

def dyna(file) :
    bp = lire_bp(file)
    tabdyna = [[0]*(bp.W+1) for i in range(bp.N+1)]
    for n in range(1, bp.N+1) :
        for w in range(bp.W+1) :
            optionA = tabdyna[n-1][w]
            optionB =  tabdyna[n-1][w-bp.poids[n-1]] + bp.val[n-1] if w >= bp.poids[n-1] else 0
            tabdyna[n][w] = max(optionA, optionB)
    return tabdyna


def bruteforce(file) :
    pass

def recover_path(tabdyna) :
    path = set()
    curr_item = len(tabdyna)-1
    curr_weight = len(tabdyna[0])-1
    while curr_item >= 0 :
        optionA = tabdyna[curr_item][curr_weight]
        #optionB = tabdyna[curr_item][curr_weight]
    pass

print(glouton('sad_1000.txt'))
print(dyna('sad_1000.txt')[-1][-1])