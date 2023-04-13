#include <iostream>
#include <string>
#include <vector>
#include <regex>
#include <fstream>

const std::vector<std::string> KEYWORDS = {"auto", "break", "case", "char", "const", "continue", "default", "do", "double",
                                           "else", "enum", "extern", "float", "for", "goto", "if", "int", "long",
                                           "register", "return", "short", "signed", "sizeof", "static", "struct",
                                           "switch", "typedef", "union", "unsigned", "void", "volatile", "while"};
const std::regex IDENTIFIER_REGEX("^[a-zA-Z_][a-zA-Z0-9_]*");
const std::regex KEYWORD_REGEX("^(auto|break|case|char|const|continue|default|do|double|else|enum|extern|float|for|goto|if|int|long|register|return|short|signed|sizeof|static|struct|switch|typedef|union|unsigned|void|volatile|while)");
const std::regex INTEGER_REGEX("^\\d+");
const std::regex FLOAT_REGEX("^(\\d+)(\\.\\d+)?");
const std::regex SYMBOL_REGEX("^(\\+|\\-|\\*|\\/|\\(|\\)|\\{|\\}|\\[|\\]|,|;|=)");

bool is_keyword(const std::string &word)
{
    for (const auto &keyword : KEYWORDS)
    {
        if (word == keyword)
        {
            return true;
        }
    }
    return false;
}

std::tuple<std::string, int, int> get_token(const std::string &code, int line_number)
{
    if (code.empty())
    {
        return std::make_tuple("", 0, line_number);
    }

    std::smatch match;
    if (std::regex_search(code, match, IDENTIFIER_REGEX))
    {
        std::string lexeme = match[0];
        if (is_keyword(lexeme))
        {
            return std::make_tuple(lexeme, 1, line_number);
        }
        else
        {
            return std::make_tuple(lexeme, 2, line_number);
        }
    }
    else if (std::regex_search(code, match, KEYWORD_REGEX))
    {
        return std::make_tuple(match[0], 1, line_number);
    }
    else if (std::regex_search(code, match, INTEGER_REGEX))
    {
        return std::make_tuple(match[0], 3, line_number);
    }
    else if (std::regex_search(code, match, FLOAT_REGEX))
    {
        return std::make_tuple(match[0], 4, line_number);
    }
    else if (std::regex_search(code, match, SYMBOL_REGEX))
    {
        return std::make_tuple(match[0], 5, line_number);
    }
    else
    {
        return std::make_tuple(std::string(1, code[0]), -1, line_number);
    }
}

std::vector<std::tuple<std::string, int, int>> lexer(const std::string &code)
{
    std::vector<std::tuple<std::string, int, int>> tokens;
    std::string line = "";
    int line_number = 1;
    int pos = 0;
    int length = code.length();
    while (pos < length)
    {
        char c = code[pos];
        if (c == '\n')
        {
            auto details =  get_token(line, line_number);

            // auto [token, type, line_num] = get_token(line, line_number);
            auto token = std::get<0>(details);
            auto type = std::get<1>(details);
            auto line_num = std::get<2>(details);
            if (type == -1)
            {
                std::cerr << "ERROR: Invalid character on line " << line_number << ": " << line << std::endl;
                return tokens;
            }
            tokens.push_back(std::make_tuple(token, type, line_num));
            line = "";
            line_number++;
        }
        else
        {
            line += c;
        }
        pos++;
    }
    auto details =  get_token(line, line_number);

    // auto [token, type, line_num] = get_token(line, line_number);
    auto token = std::get<0>(details);
    auto type = std::get<1>(details);
    auto line_num = std::get<2>(details);
    if (type == -1)
    {
        std::cerr << "ERROR: Invalid character on line " << line_number << ": " << line << std::endl;
        return tokens;
    }
    tokens.push_back(std::make_tuple(token, type, line_num));
    return tokens;
}

int main()
{
    std::string code = "";

    // read code from file
    std::string line;
    std::ifstream file("test.txt");
    if (file.is_open())
    {
        while (getline(file, line))
        {
            code += line + "\n";
        }
        file.close();
    }

    auto tokens = lexer(code);
    for (const auto &details : tokens)
    {
        auto lexeme = std::get<0>(details);
        auto token = std::get<1>(details);
        auto line_number = std::get<2>(details);
        std::cout << lexeme << " " << token << " " << line_number << std::endl;
    }
    return 0;
}