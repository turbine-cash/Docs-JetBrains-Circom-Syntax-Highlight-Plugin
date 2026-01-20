pragma circom 2.0.0;

template ComponentA() {
    signal input in1;
    signal input in2;
    signal output out1;
    signal output out2;

    var localVar1;
    var localVar2;

    component subComp = ComponentB();

    out1 <== in1 + in2;
    out2 <== in1 * in2;
}

template ComponentB() {
    signal input x;
    signal output y;
    y <== x * 2;
}

template MainCircuit() {
    signal input publicInput;
    signal input privateInput;
    signal output publicOutput;

    var intermediateVar;

    component processor = ComponentA();
    component validator = ComponentB();

    processor.in1 <== publicInput;
    processor.in2 <== privateInput;

    validator.x <== processor.out1;

    publicOutput <== processor.out2 + validator.y;
}

function helperFunction(a) {
    var result = a * 2;
    return result;
}

function anotherHelper(x, y) {
    return x + y;
}

component main {public [publicInput]} = MainCircuit();
