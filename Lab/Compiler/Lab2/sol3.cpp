#include <iostream>
#include <bits/stdc++.h>
#include <fstream>
using namespace std;

enum tokenType
{
    UNIDENTIFIED,
    INTEGER,
    IDENTIFIER,
    OPERATOR,
    DELIMITER,
    KEYWORD,
    FLOAT,
    WHITESPACE,
    STRING
};

ostream &operator<<(ostream &os, const tokenType &type)
{
    switch (type)
    {
    case INTEGER:
        os << "INTEGER";
        break;
    case IDENTIFIER:
        os << "IDENTIFIER";
        break;
    case OPERATOR:
        os << "OPERATOR";
        break;
    case DELIMITER:
        os << "DELIMITER";
        break;
    case KEYWORD:
        os << "KEYWORD";
        break;
    case FLOAT:
        os << "FLOAT";
        break;
    case UNIDENTIFIED:
        os << "UNIDENTIFIED";
        break;
    case WHITESPACE:
        os << "WHITESPACE";
        break;
    case STRING:
        os << "STRING";
        break;
    }

    return os;
}

struct positionEntry
{
    int line;
    int column;
    int length;
    tokenType type;

    positionEntry(int line, int column, int length, tokenType type)
    {
        this->line = line;
        this->column = column;
        this->length = length;
        this->type = type;
    }

    positionEntry()
    {
        this->line = 0;
        this->column = 0;
        this->length = 0;
        this->type = INTEGER;
    }

    bool operator<(const positionEntry &other) const
    {
        if (this->line < other.line)
        {
            return true;
        }
        else if (this->line == other.line)
        {
            if (this->column < other.column)
            {
                return true;
            }
        }
        return false;
    }

    bool operator==(const positionEntry &other) const
    {
        if (this->line == other.line && this->column == other.column)
        {
            return true;
        }
        return false;
    }
};

ostream &operator<<(ostream &os, const positionEntry &entry)
{
    os << "Line: " << entry.line << " Column: " << entry.column << " Length: " << entry.length << " Type: " << entry.type;
    return os;
}

class tokenizer
{
public:
    map<string, vector<positionEntry>> tokenMap;
    string filename;

    tokenizer(string filename)
    {
        this->filename = filename;
    }

    void getLines()
    {
        ifstream infile(this->filename);
        string line;
        int rowNo = 0;
        while (getline(infile, line))
        {
            rowNo++;
            parse(line, rowNo);
        }
        infile.close();
        return;
    }

    tokenType isDelimiter(char ch)
    {
        if (ch == '(' || ch == ')' || 
        ch == '{' || ch == '}' || 
        ch == '[' || ch == ']' || 
        ch == ';' || ch == ',')
        {
            return DELIMITER;
        }
        return UNIDENTIFIED;
    }

    tokenType isOperator(char ch)
    {
        if (ch == '+' || ch == '-' || 
        ch == '*' || ch == '/' || 
        ch == '%' || ch == '=' || 
        ch == '>' || ch == '<' || 
        ch == '!' || ch == '&' || 
        ch == '|' || ch == '^' || 
        ch == '~' || ch == '?')
        {
            return OPERATOR;
        }
        return UNIDENTIFIED;
    }

    tokenType isKeyword(string s)
    {
        if (s == "auto" || s == "break" || 
            s == "case" || s == "char" || 
            s == "const" || s == "continue" || 
            s == "default" || s == "do" || 
            s == "double" || s == "else" || 
            s == "enum" || s == "extern" || 
            s == "float" || s == "for" || 
            s == "goto" || s == "if" || 
            s == "int" || s == "long" || 
            s == "register" || s == "return" || 
            s == "short" || s == "signed" || 
            s == "sizeof" || s == "static" || 
            s == "struct" || s == "switch" || 
            s == "typedef" || s == "union" || 
            s == "unsigned" || s == "void" || 
            s == "volatile" || s == "while" || 
            s == "printf" || s == "scanf" || s == "main")
        {
            return KEYWORD;
        }
        return UNIDENTIFIED;
    }

    tokenType isInteger(string s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            if (s[i] < '0' || s[i] > '9')
            {
                return UNIDENTIFIED;
            }
        }
        return INTEGER;
    }

    tokenType isFloat(string s)
    {
        int dotCount = 0;
        for (int i = 0; i < s.length(); i++)
        {
            if (s[i] == '.')
            {
                dotCount++;
            }
            else if (s[i] < '0' || s[i] > '9')
            {
                return UNIDENTIFIED;
            }
        }
        if (dotCount == 1)
        {
            return FLOAT;
        }
        return UNIDENTIFIED;
    }

    tokenType isIdentifier(string s)
    {
        if (s[0] == '_' || (s[0] >= 'a' && s[0] <= 'z') || (s[0] >= 'A' && s[0] <= 'Z'))
        {
            for (int i = 1; i < s.length(); i++)
            {
                if (s[i] == '_' || (s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' && s[i] <= 'Z') || (s[i] >= '0' && s[i] <= '9'))
                {
                    continue;
                }
                else
                {
                    return UNIDENTIFIED;
                }
            }
            return IDENTIFIER;
        }
        return UNIDENTIFIED;
    }

    tokenType isString(string s)
    {
        if (s[0] == '"' && s[s.length() - 1] == '"')
        {
            return STRING;
        }
        return UNIDENTIFIED;
    }

    tokenType isWhitespace(char ch)
    {
        if (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r')
        {
            return WHITESPACE;
        }
        return UNIDENTIFIED;
    }

    string subStr(string str, int left, int right)
    {
        string s = "";
        for (int i = left; i <= right; i++)
        {
            s += str[i];
        }
        return s;
    }

    void parse(string str, int rowNo)
    {
        int left = 0;
        int right = 0;
        int column = 0;

        while (right < str.length())
        {
            if (isDelimiter(str[right]) != UNIDENTIFIED)
            {
                tokenType type;
                if (left != right)
                {
                    string s = subStr(str, left, right - 1);
                    type = isKeyword(s);
                    if (type == UNIDENTIFIED)
                    {
                        type = isInteger(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isFloat(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isIdentifier(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isString(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isWhitespace(s[0]);
                    }
                    if (type != UNIDENTIFIED)
                    {
                        positionEntry entry(rowNo, column, s.length(), type);
                        tokenMap[s].push_back(entry);
                    }
                }
                if (type != UNIDENTIFIED)
                {
                    string s = subStr(str, right, right);
                    positionEntry entry(rowNo, column + 1, s.length(), isDelimiter(s[0]));
                    tokenMap[s].push_back(entry);
                    left = right + 1;
                    right = left;
                    column++;
                }
                else
                {
                    right++;
                    column++;
                }
            }
            else if (isOperator(str[right]) != UNIDENTIFIED)
            {
                tokenType type;
                if (left != right)
                {
                    string s = subStr(str, left, right - 1);
                    type = isKeyword(s);
                    if (type == UNIDENTIFIED)
                    {
                        type = isInteger(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isFloat(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isIdentifier(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isString(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isWhitespace(s[0]);
                    }
                    if (type != UNIDENTIFIED)
                    {
                        positionEntry entry(rowNo, column, s.length(), type);
                        tokenMap[s].push_back(entry);
                    }
                }

                if (type != UNIDENTIFIED)
                {
                    string s = subStr(str, right, right);
                    positionEntry entry(rowNo, column + 1, s.length(), isOperator(s[0]));
                    tokenMap[s].push_back(entry);
                    left = right + 1;
                    right = left;
                    column++;
                }
                else
                {
                    right++;
                    column++;
                }
            }

            else if (isWhitespace(str[right]) != UNIDENTIFIED)
            {
                tokenType type;
                if (left != right)
                {
                    string s = subStr(str, left, right - 1);
                    type = isKeyword(s);
                    if (type == UNIDENTIFIED)
                    {
                        type = isInteger(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isFloat(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isIdentifier(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isString(s);
                    }
                    if (type == UNIDENTIFIED)
                    {
                        type = isWhitespace(s[0]);
                    }
                    if (type != UNIDENTIFIED)
                    {
                        positionEntry entry(rowNo, column, s.length(), type);
                        tokenMap[s].push_back(entry);
                    }
                }

                if (type != UNIDENTIFIED)
                {
                    string s = subStr(str, right, right);
                    positionEntry entry(rowNo, column + 1, s.length(), isWhitespace(s[0]));
                    tokenMap[s].push_back(entry);
                    left = right + 1;
                    right = left;
                    column++;
                }
                else
                {
                    right++;
                    column++;
                }
            }
            else
            {
                right++;
                column++;
            }
        }

        if(left!=right){
            cout<<"ERROR IN LINE "<<rowNo<<endl<<endl;
        }
    }
};

int main()
{
    string filename;
    cin >> filename;
    tokenizer t(filename);

    t.getLines();

    for (auto it = t.tokenMap.begin(); it != t.tokenMap.end(); it++)
    {
        if (it->first != " ")
        {
            cout << it->first << endl;
            for (int i = 0; i < it->second.size(); i++)
            {
                cout <<  "Line " << it->second[i].line << "| Column " << it->second[i].column << "| Length of " << it->second[i].length << " | " << it->second[i].type << endl;
            }
            cout << endl;
        }
    }
}