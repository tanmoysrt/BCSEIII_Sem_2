#include <bits/stdc++.h>
using namespace std;

// Function to check if a character is a valid identifier character
bool isIdentifierChar(char c) {
    return isalnum(c) || c == '_';
}

// Function to check if a character is a valid digit
bool isDigit(char c) {
    return isdigit(c);
}

map<string, pair<vector<pair<int, int>>, string>> symTab;

// Function to tokenize a string
void tokenize(string input, int row) {
    int i = 0;
    while (i < input.length()) {
        // Skip whitespace
        if (isspace(input[i])) {
            i++;
            continue;
        }

        // Check for keywords or identifiers
        if (isalpha(input[i])/* || input[i] == '_'*/) {
            string lexeme = "";
            while (isIdentifierChar(input[i])) {
                lexeme += input[i];
                i++;
            }

            if (lexeme == "int") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else if (lexeme == "char") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else if (lexeme == "string") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else if (lexeme == "main") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else if (lexeme == "get") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else if (lexeme == "put") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else if (lexeme == "return") {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Keyword";
            } else {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "ID";
            }
            continue;
        }

        // Check for integer constants
        if (isDigit(input[i])) {
            string lexeme = "";
            while (isDigit(input[i])) {
                lexeme += input[i];
                i++;
            }
            symTab[lexeme].first.push_back({row, i});
            symTab[lexeme].second = "Numerical Constant";
            continue;
        }

        // Check for character constants
        if (input[i] == '\'') {
            string lexeme = "";
            lexeme += input[i];
            i++;
            if (i >= input.length()) {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Unknown";
                continue;
            }
            lexeme += input[i];
            i++;
            if (i >= input.length() || input[i] != '\'') {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Unknown";
                continue;
            }
            lexeme += input[i];
            i++;
            symTab[lexeme].first.push_back({row, i});
            symTab[lexeme].second = "Character Constant";
            continue;
        }

        // Check for string constants
        if (input[i] == '\"') {
            string lexeme = "";
            lexeme += input[i];
            i++;
            while (i < input.length() && input[i] != '\"') {
                lexeme += input[i];
                i++;
            }
            if (i >= input.length()) {
                symTab[lexeme].first.push_back({row, i});
                symTab[lexeme].second = "Unknown";
                continue;
            }
            lexeme += input[i];
            i++;
            symTab[lexeme].first.push_back({row, i});
            symTab[lexeme].second = "String Constant";
            continue;
        }

        if (input[i] == '=') {
            if (i+1 < input.length() && input[i+1] == '=') {
                symTab["=="].first.push_back({row, i});
                symTab["=="].second = "Relational Operator";
                i += 2;
            } else {
                symTab["="].first.push_back({row, i});
                symTab["="].second = "Arithmetic Operator";
                i++;
            }
            continue;
        }

        if (input[i] == '>') {
            symTab[">"].first.push_back({row, i});
            symTab[">"].second = "Relational Operator";
            i++;
            continue;
        }

        if (input[i] == '<') {
            symTab["<"].first.push_back({row, i});
            symTab["<"].second = "Relational Operator";
            i++;
            continue;
        }

        if (input[i] == '+') {
            symTab["+"].first.push_back({row, i});
            symTab["+"].second = "Arithmetic Operator";
            i++;
            continue;
        }

        if (input[i] == '-') {
            symTab["-"].first.push_back({row, i});
            symTab["-"].second = "Arithmetic Operator";
            i++;
            continue;
        }

        if (input[i] == '*') {
            symTab["*"].first.push_back({row, i});
            symTab["*"].second = "Arithmetic Operator";
            i++;
            continue;
        }

        // Check for ternary operator
        if (i+1 < input.length() && input[i] == '?' && input[i+1] == ':') {
            symTab["?:"].first.push_back({row, i});
            symTab["?:"].second = "Ternary Operator";
            i += 2;
            continue;
        }

        // Check for other tokens
        if (input[i] == '(') {
            symTab["("].first.push_back({row, i});
            symTab["("].second = "Delimiter";
            i++;
            continue;
        }

        if (input[i] == '{') {
            symTab["{"].first.push_back({row, i});
            symTab["{"].second = "Delimiter";
            i++;
            continue;
        }

        if (input[i] == '}') {
            symTab["}"].first.push_back({row, i});
            symTab["}"].second = "Delimiter";
            i++;
            continue;
        }

        if (input[i] == ')') {
            symTab[")"].first.push_back({row, i});
            symTab[")"].second = "Delimiter";
            i++;
            continue;
        }

        if (input[i] == ';') {
            symTab[";"].first.push_back({row, i});
            symTab[";"].second = "Delimiter";
            i++;
            continue;
        }

        // Unknown token
        symTab[string(1, input[i])].first.push_back({row, i});
        symTab[string(1, input[i])].second = "Unknown";
        i++;
    }
}

int main() {
    symTab.clear();

    ifstream MyReadFile("test.txt");
    string myline;
    int row = 1;
    
    while (getline(MyReadFile, myline)) {
        tokenize(myline, row);
        row++;
    }

    ofstream outfile;
    outfile.open("tokenized.txt", std::ios_base::out);

    for (const auto &[key, value] : symTab) {
        outfile << "Lexeme:  " << key;
        outfile << "\nType:  " << symTab[key].second << endl;

        for (pair<int, int> x : symTab[key].first) {
            outfile << "Line No.:  " << x.first << "   Column No.:  " << x.second << endl;
        }

        outfile << endl;
    }
    
    cout << endl;
    return 0;
}