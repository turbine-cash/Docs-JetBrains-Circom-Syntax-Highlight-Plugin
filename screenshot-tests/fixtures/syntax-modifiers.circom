pragma circom 2.0.0;
pragma custom_templates;

template parallel ParallelAdder(n) {
    signal input in[n];
    signal output out;

    var sum = 0;
    for (var i = 0; i < n; i++) {
        sum += in[i];
    }
    out <== sum;
}

template custom CustomGate() {
    signal input a;
    signal input b;
    signal output c;
    c <== a * b;
}

bus DataBus(width) {
    signal data[width];
    signal valid;
}
