## `[a-zA-ZáàâãéèêíïóôõöúüçÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇ\s]+` -> nome

- `[ ... ]` → define um **conjunto de caracteres permitidos**.
- `a-zA-Z` → letras do alfabeto básico em **minúsculo** e **maiúsculo**.
- `áàâãéèêíïóôõöúüç` → vogais e cedilha com **acentos em minúsculo**.
- `ÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇ` → as mesmas, mas em **maiúsculo** (inclui trema também).
- `\s` → qualquer **espaço em branco** (espaço normal, tab, quebra de linha...).
- `+` → significa **“um ou mais”** desses caracteres.


## `^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$` -> email

- `^` → indica o **início da string**.
- `[a-zA-Z0-9._%+-]+` → corresponde ao **nome do usuário** (antes do `@`), aceitando:
    - letras maiúsculas e minúsculas (`a-zA-Z`)
    - dígitos (`0-9`)
    - ponto (`.`), sublinhado (`_`), porcentagem (`%`), mais (`+`) e hífen (`-`)
    - `+` significa **um ou mais** desses caracteres.
- `@` → exige que exista um **arroba** separando usuário e domínio.
- `[a-zA-Z0-9.-]+` → corresponde ao **nome do domínio**, aceitando letras, números, ponto (`.`) e hífen (`-`).
- `\.` → exige um **ponto literal** antes da extensão.
- `[a-zA-Z]{2,}` → corresponde à **extensão do domínio** (ex: `com`, `br`, `org`), devendo ter **pelo menos 2 letras**.
- `$` → indica o **fim da string**.


- `\D` → corresponde a **qualquer caractere que NÃO seja um dígito** (0–9).

## `(\\d)\\1{10}")` -> cpf

- `(\\d)` → captura **um dígito qualquer** (0–9) e o coloca em um **grupo de captura**.
- `\\1` → referência ao **mesmo dígito capturado antes** (backreference).
- `{10}` → significa que esse dígito deve se repetir **10 vezes** seguidas.