with open("01.input") as f:
    xs = [ (-1 if s[0] == "L" else 1) * int(s[1:]) for s in f.read().split()]

def run_1(xs):
    at = 50
    for x in xs:
        at = (at + x) % 100
        yield at

print(sum(x == 0 for x in run_1(xs)))

def sign(x): return 1 if x > 0 else -1

def run_2(xs):
    at = 50
    for x in xs:
        d = sign(x)
        for _ in range(abs(x)):
            at += d
            at %= 100
            yield at

print(sum(x == 0 for x in run_2(xs)))
