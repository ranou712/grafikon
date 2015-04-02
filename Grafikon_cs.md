# Grafikon #
Pokud máte nějaké připomínky, nápady můžete je vyjádřit v komentářích k této stránce nebo poslat na adresu: _grafikon<zavináč>parostroj<tečka>net_. Pokud máte vytvořen grafikon, který bych mohl použít pro testovaní, můžete ho poslat taktéž.

[Návod](http://ostramo.parostroj.net/clanky/Grafikon/grafikon.html) lze nalézt na stránkach Parostroje.

[Soubory ke stažení](https://drive.google.com/folderview?id=0B5U-kGpXMXfNRWZkWXhTbzdhamc&usp=sharing)

## Verze ##
### Grafikon-0.14 (development version) ###
  * [Grafikon 0.14 Web Start](http://jub.parostroj.net/grafikon/0.14/grafikon.jnlp) - soubor pro spuštění programu přímo z webu
  * [Změny](http://code.google.com/p/grafikon/source/list)

### Grafikon-0.13 ###
  * [Grafikon 0.13 Web Start](http://jub.parostroj.net/grafikon/0.13/grafikon.jnlp) - soubor pro spuštění programu přímo z webu

### Grafikon-0.12 ###
  * [Grafikon 0.12 Web Start](http://jub.parostroj.net/grafikon/0.12/grafikon.jnlp) - soubor pro spuštění programu přímo z webu

---


### Starší stabilní verze ###
  * [Grafikon Web Start](http://jub.parostroj.net/grafikon/grafikon.jnlp) - soubor pro spuštění programu přímo z webu
  * [grafikon.zip](http://jub.parostroj.net/grafikon/grafikon.zip) - archív pro offline spuštění

### Grafikon-0.10 ###
  * [Grafikon 0.10 Web Start](http://jub.parostroj.net/grafikon/0.10/grafikon.jnlp) - soubor pro spuštění programu přímo z webu
  * [Grafikon-0.10](http://code.google.com/p/grafikon/downloads/list?q=label:0.10) - archív pro offline spuštění

### Grafikon-0.11 ###
  * [Grafikon 0.11 Web Start](http://jub.parostroj.net/grafikon/0.11/grafikon.jnlp) - soubor pro spuštění programu přímo z webu
  * [Grafikon-0.11](http://code.google.com/p/grafikon/downloads/list?q=label:0.11) - archív pro offline spuštění

## Vlastnosti ##
### Java Web Start ###
Při spuštění aplikace přes ikonu na ploše (či menu) se aplikace nemusí korektně aktualizovat pokud jsou změny přímo v JNLP souboru. Pro aktualizaci programu je pak ho nutné spustit přes odkaz na JNLP soubor na webu (nebo z příkazové řádky `javaws http://jub.parostroj.net/grafikon/0.11/grafikon.jnlp`). Díky chybě v Java Web Start systému může následně dojít k nefunkčnosti ikony na ploše. Odstranit tento problém lze pomocí odinstalování aplikace (`javaws -uninstall http://jub.parostroj.net/grafikon/0.11/grafikon.jnlp` z příkazové řádky nebo přes příkaz _javaws -viewer_ a pak výběr aplikace kterou chceme odinstalovat). Následná instalace pak nainstaluje ikonu s korektním odkazem na plochu a do menu.

### Zátěžové tabulky ###
Příklad zátěžových tabulek a ukázkový grafikon s jejich použitím lze stáhnout [zde](http://code.google.com/p/grafikon/downloads/list). Soubour engines\_example...gtm obsahuje zátěžové tabulky pro některé řady hnacích vozidel. Jejich použití v nově vytvářeném grafikonu je jednoduché - stačí v menu vybrat _Soubor -> Import ..._, vybrat hnací vozidla, přesunout do pravé části okna a potvrdit. Předpokladem je mít definovány nejdříve třidy tratí, které lze současně s hnacími vozidly importovat také. Soubor _02\_2011\_ep3.gtm_ z příkladů obsahuje ukázkové použití pro klubový provoz v OstraMu.

Použití je pak jednoduché - trati se nastaví její třída a oběhu hnací vozidla jeho typ. Všechny ostatní informace počítá program automaticky.

### Popis ###
Aktuálně by spuštění mělo fungovat přímo kliknutím na odkaz, pokud ne, pak je nutno _grafikon.jnlp_ soubor stáhnout (uložit na disk) a pak teprve spustit:
  * uložit soubor z předchozího odkazu na disk (např. _c:\grafikon\grafikon.jnlp_)
  * otevřít oblíbený souborový manager - Explorer, TotalCommander, ... a pak dvakrát kliknout (nutno mít nainstalováno JRE - viz níže) na _grafikon.jnlp_
  * nebo spustit pomoci _javaws grafikon.jnlp_ z příkazové řádky
  * počkat až se program spustí - musí sám sebe nahrát z webu, takže to může chvíli trvat
  * po prvním spuštění je schopen fungovat i bez připojení na web, ale pouze na daném počítači

Spustit lze také přímo z příkazové řádky (pro verzi 0.10): `javaws http://jub.parostroj.net/grafikon/0.10/grafikon.jnlp`

Také je možno stáhnout zazipovaný soubor pro offline spuštění:
  * stáhnout soubor
  * rozbalit grafikon.zip do nějakého adresáře (např. _c:\grafikon\_)
  * dvakrát kliknout (nutno mít nainstalováno JRE - viz níže) na _grafikon.jar_ nebo do příkazové řádky zadat _java -jar grafikon.jar_
  * počkat až se program spustí

V obou dvou předchozích případech je nutno naistalovat [Java JRE 6](http://www.java.com/en/download/manual.jsp) (Java Runtime Environment - více informací viz odkaz).

Překlady:
  * slovenský - Peter Meszároš
  * polský - Marcin Miś

Pro vložení textu na konec sešitového jízdního řádu je možné použít upravený [BBCode](BBCode.md).

Upozornění: Pokud start programu trvá déle než minutu (samotný start, ne stažení z webu) nebo při pokusu o otevření dialogového okna pro nahrání/uložení souboru s grafikon se program zasekne na delší dobu, pak je s největší pravděpodobností způsoben chybou v Javě při inicializaci okna pro nahrávání a ukládání souborů. Zkontrolujte zda na ploše nemáte nějaké zip soubory. Pokud ano, pak je buď přesuňte to jiného adresáře nebo smažte. Také každý přístup do adresáře se zip soubory je pomalejší (tomuto se nedá zabránit).

Licence: [GPL](http://www.gnu.org/licenses/gpl.txt)

Zdrojové soubory jsou dostupné jako mercurial repository na adrese (Maven projekt):
http://code.google.com/p/grafikon

Kopii zdrojových souborů lze získat příkazem:
`hg clone http://grafikon.googlecode.com/hg grafikon`