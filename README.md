# Projekt-PP-Wyszukiwanie-tekstu
Wykowski Kamil nr. albumu 51404 Grupa L2

Projekt nr. 5 - Wyszukiwanie tekstu

W skład projektu wchodzi plik main oraz kilka przykładowych plików tekstowych na których prenentowane będzie działanie kodu.
Do uruchomienia kodu nie potrzebne jest dodatkowe oprogrmowanie, ani instalacja dodatkowych paczek.

Zaimplementowane funkcjonalności:

- do stworzenia panelu zostały wykorzystane JPanel JTextField oraz JOptionPane

- wyszukiwanie plików z rozszerzeniem .txt za pomocą Files.walkFileTree, następnie dla każdego znalezionego pliku wykonywana jest metoda searchFile

- w przypadku znalezienia lub nie znalezienia porządnego tekstu wyświetlają się okna infromacyjne tworzone dzięki JTextArea oraz JScrollPane

- boolean searchFile przeszukuje i odczytuje linie tekstu konwertuje na małe litery, a następnie dopasowuje do poszukiwanego tekstu

- convertPatternToRegex zostało wykorzystane do ustalenia wzorca wyszkiwania, znak "?" może być użyty za każdy iinny znak, natomiast * jako oznaczenie ciągu znaków 
