inp = [tuple(map(int, x.split("-"))) for x in input().split(",")]

def is_same_twice(s):
    if len(s) % 2: return False
    return s[:len(s) // 2] == s[len(s) // 2:]

print(sum( a for (lo, hi) in inp for a in range(lo, hi + 1) if is_same_twice(str(a))))
