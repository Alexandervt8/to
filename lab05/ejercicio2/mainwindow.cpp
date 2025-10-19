#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QListWidgetItem>
#include <QVariant>
#include <QStringList>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    populateCountries();

    connect(ui->countriesListWidget, &QListWidget::itemClicked,
            this, &MainWindow::onCountryClicked);

    ui->infoLabel->setText("Seleccione un país desde la lista");
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::populateCountries()
{

    struct Country { QString name, capital, language; };
    const Country countries[] = {
        { "Perú", "Lima", "Español" },
        { "España", "Madrid", "Español" },
        { "Francia", "París", "Francés" },
        { "Alemania", "Berlín", "Alemán" },
        { "Japón", "Tokio", "Japonés" },
        { "Estados Unidos", "Washington D.C.", "Inglés" }
    };

    for (const auto &c : countries) {
        QListWidgetItem *it = new QListWidgetItem(c.name, ui->countriesListWidget);

        it->setData(Qt::UserRole, c.capital);
        it->setData(Qt::UserRole + 1, c.language);

        it->setToolTip(c.capital + " — " + c.language);
        ui->countriesListWidget->addItem(it);
    }
}

void MainWindow::onCountryClicked(QListWidgetItem *item)
{
    if (!item) return;
    QString capital = item->data(Qt::UserRole).toString();
    QString language = item->data(Qt::UserRole + 1).toString();
    QString text = QString("<b>%1</b><br>Capital: %2<br>Idioma: %3")
                       .arg(item->text(), capital, language);

    ui->infoLabel->setText(text);


}
