brew -v && echo "***brew is allready installed" || /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
echo "***installing latest chromedriver"
brew tap homebrew/cask && brew cask reinstall chromedriver
