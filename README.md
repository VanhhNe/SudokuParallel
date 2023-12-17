# SudokuParallel

## Hướng dẫn chạy Sudoku Game 
Ta sẽ truy cập vào đường dẫn SudokuParallel, và biên dịch các file
- Bước 1: Biên dịch chương trình bằng lệnh: javac Main/*.java
- Bước 2: Chạy chương trình bằng: java Main.SudokuSolver

## Hướng dẫn chạy So sánh thuật toán
- Bước 1: Biên dịch chương trình bằng lệnh: javac ResearchComputation/*.java
- Bước 2: Chạy chương trình bằng lệnh: java ResearchComputation.MainRunner <file_testcase.txt> <option> <kernel>  
Trong đó:
- file_testcase.txt: được lưu trong thư mục Test_Cases
- option: Là lựa chọn mô hình muốn chạy bao gồm SequentialBacktracking (0), SequentialBruteForce (1), ParallelBruteForce (2). Hiện option (2) chưa hoàn thiện
- kernel: Chỉ cần khi lựa chọn mô hình ParallelBruteForce (2)  
Ví dụ: javac ResearchComputation/*.java && java ResearchComputation.MainRunner Test_Cases/9x9_easy.txt 2 4
