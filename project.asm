(KEYBOARD)
//Project
    @SCREEN
    D = A
    @14
    D = D + A
    @addr
    M = D

    @256
    D = A
    @ir
    M = D

    @14
    D = A
    @cv
    M = D

    @8192
    D = A
    @null
    M = D

    @KBD
    D = M
    @MIDDLE1
    D; JNE

    @EMPTY
    0; JMP

(RESETVER)
    @256
    D = A
    @ir
    M = D

    @cv
    M = M + 1
    D = M
    @SCREEN
    D = D + A
    @addr
    M = D

    @15
    D = A
    @cv
    D = D - M
    @MIDDLE2
    D; JEQ

    @16
    D = A
    @cv
    D = D - M
    @MIDDLE3
    D; JEQ

    @17
    D = A
    @cv
    D = D - M
    @MIDDLE4
    D; JEQ

(MIDDLE1)
    @addr
    A = M
    M = -1

    @ir
    M = M - 1
    D = M
    @RESETVER
    D; JEQ

    @32
    D = A
    @addr
    M = M + D

    @MIDDLE1
    0; JMP

(MIDDLE2)
    @addr
    A = M
    M = -1

    @ir
    M = M - 1
    D = M
    @RESETVER
    D; JEQ

    @32
    D = A
    @addr
    M = M + D

    @MIDDLE2
    0; JMP

(MIDDLE3)
    @addr
    A = M
    M = -1

    @ir
    M = M - 1
    D = M
    @RESETVER
    D; JEQ

    @32
    D = A
    @addr
    M = M + D

    @MIDDLE3
    0; JMP

(MIDDLE4)
    @addr
    A = M
    M = -1

    @ir
    M = M - 1
    D = M
    @HORIZONTAL
    D; JEQ

    @32
    D = A
    @addr
    M = M + D

    @MIDDLE4
    0; JMP

(HORIZONTAL)
    @SCREEN
    D = A
    @3072
    D = D + A
    @addr
    M = D

    @64
    D = A
    @ir
    M = D

    @0
    D = A
    @cv
    M = D

    @LOOP
    0; JMP

(LOOP)
    @addr
    A = M
    M = -1

    @ir
    MD = M - 1
    @RESETHOR
    D; JEQ

    @32
    D = A
    @addr
    M = M + D

    @LOOP
    0; JMP

(RESETHOR)
    @cv
    M = M + 1

    @32
    D = A
    @cv
    D = D - M
    @KEYBOARD
    D; JEQ

    @cv
    D = M
    @SCREEN
    D = D + A
    @3072
    D = D + A
    @addr
    M = D

    @64
    D = A
    @ir
    M = D

    @LOOP
    0; JMP

(EMPTY)
    @SCREEN
    D = A
    @addr
    M = D

    @WHITE
    0; JMP

(WHITE)
    @addr
    A = M
    M = 0

    @addr
    M = M + 1

    @null
    MD = M - 1
    @WHITE
    D; JGT

    @KEYBOARD
    0; JMP
