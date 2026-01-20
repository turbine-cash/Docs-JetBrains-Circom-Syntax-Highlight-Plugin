pragma circom 2.0.0;

template SignalDemo() {
    signal input publicValue;
    signal input secretKey;
    signal output commitment;
    signal output nullifier;

    signal intermediate;

    intermediate <== publicValue * secretKey;
    commitment <== intermediate + 1;
    nullifier <== intermediate - 1;
}
