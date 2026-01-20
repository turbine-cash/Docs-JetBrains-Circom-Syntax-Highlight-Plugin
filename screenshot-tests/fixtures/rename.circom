pragma circom 2.0.0;

template OriginalName() {
    signal input value;
    signal output doubled;

    doubled <== value * 2;
}

template Consumer() {
    signal input x;
    signal output y;

    component instance = OriginalName();
    instance.value <== x;
    y <== instance.doubled;
}

component main = Consumer();
